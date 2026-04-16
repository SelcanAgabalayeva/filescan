package com.filescan.filescan.services;

import com.filescan.filescan.dtos.AuthResponse;
import com.filescan.filescan.dtos.LoginDto;
import com.filescan.filescan.dtos.RegisterDto;
import jakarta.validation.Valid;

public interface AuthService {
    AuthResponse register(@Valid RegisterDto request);

    AuthResponse login(@Valid LoginDto request);

    void logout(String token);
}
