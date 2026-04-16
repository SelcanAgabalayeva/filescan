package com.filescan.filescan.services.impls;

import com.filescan.filescan.dtos.AuthResponse;
import com.filescan.filescan.dtos.LoginDto;
import com.filescan.filescan.dtos.RegisterDto;
import com.filescan.filescan.dtos.UserResponse;
import com.filescan.filescan.model.Role;
import com.filescan.filescan.model.User;
import com.filescan.filescan.repositories.UserRepository;
import com.filescan.filescan.security.JwtUtil;
import com.filescan.filescan.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtBlacklistService blacklistService;
    @Override
    public AuthResponse register(RegisterDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(mapToResponse(user))
                .build();
    }

    @Override
    public AuthResponse login(LoginDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(mapToResponse(user))
                .build();
    }

    @Override
    public void logout(String token) {
        blacklistService.blacklist(token, 86400000);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

}
