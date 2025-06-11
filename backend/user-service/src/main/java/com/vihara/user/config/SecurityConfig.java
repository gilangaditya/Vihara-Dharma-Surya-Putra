// src/main/java/com/vihara/user/config/SecurityConfig.java
package com.vihara.user.config;

import org.springframework.security.web.AuthenticationEntryPoint;
import com.vihara.user.security.jwt.AuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Untuk @PreAuthorize pada method
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint,
                          AuthenticationFilter authenticationFilter,
                          AuthenticationProvider authenticationProvider) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Nonaktifkan CSRF untuk aplikasi stateless (REST API)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint)) // Handle unauthorized access
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Gunakan session stateless
            .authorizeHttpRequests(authorize ->
                    authorize
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll() // Izinkan akses ke /api/auth/** (login, register) tanpa autentikasi
                        .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll() // Contoh public API
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll() // Untuk Swagger/OpenAPI
                        .anyRequest().authenticated() // Semua permintaan lain harus diautentikasi
            );

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class); // Tambahkan JWT filter sebelum filter username/password

        return http.build();
    }
}