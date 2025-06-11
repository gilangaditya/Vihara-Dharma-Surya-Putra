// src/main/java/com/vihara/user/dto/LoginRequest.java
package com.vihara.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username or email cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}