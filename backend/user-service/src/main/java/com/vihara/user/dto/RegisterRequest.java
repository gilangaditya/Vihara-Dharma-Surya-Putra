// src/main/java/com/vihara/user/dto/RegisterRequest.java
package com.vihara.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set; // Import Set

@Data // Anotasi Lombok akan menghasilkan getter/setter
public class RegisterRequest {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;

    private Set<String> role; // ****** INI ADALAH CARA YANG BENAR UNTUK MENGAMBIL ROLES ******

    // Hapus method getRole() yang manual, Lombok akan meng-generatenya secara otomatis.
    // public String getRole() {
    // throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    // }
}
