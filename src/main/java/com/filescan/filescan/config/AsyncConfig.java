package com.filescan.filescan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "fileScanExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);     // minimum thread
        executor.setMaxPoolSize(10);     // maksimum thread
        executor.setQueueCapacity(100);  // queue size
        executor.setThreadNamePrefix("FileScan-");
        executor.initialize();
        return executor;
    }


}
