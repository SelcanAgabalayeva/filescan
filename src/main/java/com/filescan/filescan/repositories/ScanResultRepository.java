package com.filescan.filescan.repositories;

import com.filescan.filescan.model.ScanResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScanResultRepository extends JpaRepository<ScanResult, Long> {
}
