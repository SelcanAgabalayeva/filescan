package com.filescan.filescan.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService {

    private final StringRedisTemplate redis;

    public void blacklist(String token, long ttl) {
        redis.opsForValue().set(token, "blacklisted", Duration.ofMillis(ttl));
    }

    public boolean isBlacklisted(String token) {
        return redis.hasKey(token);
    }
}
