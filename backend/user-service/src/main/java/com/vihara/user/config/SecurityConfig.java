package com.vihara.user.config;

import com.vihara.user.security.jwt.JwtAuthenticationEntryPoint;
import com.vihara.user.security.jwt.AuthenticationFilter;
import com.vihara.user.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager; // JANGAN HAPUS IMPORT INI
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // JANGAN HAPUS IMPORT INI
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // JANGAN HAPUS IMPORT INI
import org.springframework.security.crypto.password.PasswordEncoder; // JANGAN HAPUS IMPORT INI
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final AuthenticationFilter authTokenFilter;

    // PasswordEncoder akan diinjeksikan dari ApplicationConfig
    private final PasswordEncoder passwordEncoder; // Tambahkan injeksi ini

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                          JwtAuthenticationEntryPoint unauthorizedHandler,
                          AuthenticationFilter authTokenFilter,
                          PasswordEncoder passwordEncoder) { // Injeksi passwordEncoder
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.authTokenFilter = authTokenFilter;
        this.passwordEncoder = passwordEncoder; // Set di constructor
    }

    // ****** HAPUS @Bean passwordEncoder() DI SINI ******
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder); // Gunakan instance yang diinjeksikan
        return authProvider;
    }

    // ****** HAPUS @Bean authenticationManager() DI SINI ******
    // @Bean
    // public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    //     return authConfig.getAuthenticationManager();
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/test/**").permitAll()
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
