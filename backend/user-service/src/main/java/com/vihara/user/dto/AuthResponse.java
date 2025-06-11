// src/main/java/com/vihara/user/dto/AuthResponse.java
package com.vihara.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role; // Role of the logged-in user (e.g., "ROLE_SISWA", "ROLE_ADMIN")
    private String username;

    // Added fields to match constructor
    private String token;
    private Long id;
    private String email;
    private List<String> roles;

    // Add this constructor if it does not exist
    public AuthResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}