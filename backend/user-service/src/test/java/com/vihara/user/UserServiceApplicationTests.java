package com.vihara.user;

import io.jsonwebtoken.SignatureAlgorithm; // Import ini
import io.jsonwebtoken.security.Keys;     // Import ini
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64; // Import ini

@SpringBootTest // Atau @RunWith(SpringRunner.class) dan @SpringBootTest untuk JUnit 4
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
        // Ini adalah test kosong untuk memastikan konteks Spring Boot bisa dimuat
    }

    @Test // ****** TAMBAHKAN METHOD TEST INI ******
    void generateJwtSecretKey() {
        // Ini akan menghasilkan kunci 256-bit yang aman untuk HS256
        String secretString = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
        System.out.println("\nGenerated JWT Secret (Base64 encoded): " + secretString + "\n");
    }
    // ********************************************
}