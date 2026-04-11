package com.filescan.filescan.services;

import com.filescan.filescan.model.ScanResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface FileScanService {
    CompletableFuture<ScanResult> scanFile(MultipartFile file);
}
