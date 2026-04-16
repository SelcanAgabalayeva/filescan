package com.filescan.filescan.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank(message = "Full name boş ola bilməz")
    @Size(min = 3, message = "Full name minimum 3 simvol olmalıdır")
    private String fullName;

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Email düzgün formatda olmalıdır")
    private String email;

    @NotBlank(message = "Password boş ola bilməz")
    @Size(min = 6, message = "Password minimum 6 simvol olmalıdır")
    private String password;
}

