package com.filescan.filescan.services.impls;

import com.filescan.filescan.exceptions.VirusDetectedException;
import com.filescan.filescan.model.ScanResult;
import com.filescan.filescan.repositories.ScanResultRepository;
import com.filescan.filescan.services.FileScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileScanServiceImpl implements FileScanService {

    private final ScanResultRepository repository;

    private static final String CLAMAV_HOST = "localhost";
    private static final int CLAMAV_PORT = 3310;

    @Async
    @Override
    public CompletableFuture<ScanResult> scanFile(MultipartFile file) {

        try (Socket socket = new Socket(CLAMAV_HOST, CLAMAV_PORT);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {

            // ClamAV STREAM command
            out.write("zINSTREAM\0".getBytes());
            out.flush();

            byte[] buffer = file.getBytes();
            int size = buffer.length;

            out.write(ByteBuffer.allocate(4).putInt(size).array());
            out.write(buffer);
            out.write(ByteBuffer.allocate(4).putInt(0).array());
            out.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String response = reader.readLine();

            // 🔥 ƏN VACİB HİSSƏ
            String cleanResponse = response.replace("\0", "");

            boolean infected = cleanResponse.contains("FOUND");

            ScanResult result = new ScanResult();
            result.setFileName(file.getOriginalFilename());
            result.setInfected(infected);
            result.setMessage(cleanResponse); // ✅ BURADA dəyişdik
            result.setScannedAt(LocalDateTime.now());

            repository.save(result);

            if (infected) {
                throw new VirusDetectedException("Virus detected: " + cleanResponse);
            }

            return CompletableFuture.completedFuture(result);

        } catch (IOException e) {
            throw new RuntimeException("ClamAV connection failed");
        }
    }
}
