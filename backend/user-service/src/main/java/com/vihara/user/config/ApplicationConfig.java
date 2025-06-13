package com.vihara.user.config;

import com.vihara.user.repository.UserRepository; // Import UserRepository jika dibutuhkan
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider; // Import AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService; // Import UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    // Jika Anda belum memiliki kelas UserDetailsServiceImpl, ini adalah cara untuk membuatnya
    // Jika Anda sudah memiliki UserDetailsServiceImpl terpisah, maka Anda tidak perlu Bean ini di sini.
    // Berdasarkan log, Anda sudah punya UserDetailsServiceImpl, jadi kita mungkin tidak perlu ini.
    // @Bean
    // public UserDetailsService userDetailsService(UserRepository userRepository) {
    //    return new UserDetailsServiceImpl(userRepository);
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Ini bean yang penting. Akan diinject oleh SecurityConfig.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Anda juga bisa mendefinisikan AuthenticationProvider di sini jika Anda mau
    // Namun, kita sudah mendefinisikannya di SecurityConfig. Biarkan saja di sana
    // untuk sekarang agar mudah.
    // @Bean
    // public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
    //    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //    authProvider.setUserDetailsService(userDetailsService);
    //    authProvider.setPasswordEncoder(passwordEncoder());
    //    return authProvider;
    // }
}
