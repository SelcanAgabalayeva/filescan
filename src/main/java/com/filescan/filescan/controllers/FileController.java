package com.filescan.filescan.controllers;

import com.filescan.filescan.exceptions.VirusDetectedException;
import com.filescan.filescan.model.ScanResult;
import com.filescan.filescan.services.FileScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileScanService fileScanService;
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            CompletableFuture<ScanResult> future = fileScanService.scanFile(file);

            ScanResult result = future.join();

            return ResponseEntity.ok(result);

        } catch (VirusDetectedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
