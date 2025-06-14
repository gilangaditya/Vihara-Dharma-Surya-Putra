package com.vihara.profile.controller;

import com.vihara.profile.model.ViharaProfile;
import com.vihara.profile.service.ViharaProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Akan digunakan untuk otorisasi
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile-vihara") 
public class ViharaProfileController {

    private final ViharaProfileService viharaProfileService;

    public ViharaProfileController(ViharaProfileService viharaProfileService) {
        this.viharaProfileService = viharaProfileService;
    }

    // Endpoint untuk mendapatkan semua profil
    @GetMapping
    public ResponseEntity<List<ViharaProfile>> getAllProfiles() {
        List<ViharaProfile> profiles = viharaProfileService.findAllProfiles();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    // Endpoint untuk mendapatkan profil berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<ViharaProfile> getProfileById(@PathVariable Long id) {
        return viharaProfileService.findProfileById(id)
                .map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint untuk mendapatkan profil utama (jika ada konsep profil tunggal)
    // Misalnya, jika hanya ada satu entri profil vihara yang ingin ditampilkan di landing page
    @GetMapping("/main")
    public ResponseEntity<ViharaProfile> getMainProfile() {
        return viharaProfileService.findMainProfile()
                .map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Endpoint untuk membuat profil baru (hanya Admin yang boleh)
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Hanya ROLE_ADMIN yang bisa membuat profil
    public ResponseEntity<ViharaProfile> createProfile(@Valid @RequestBody ViharaProfile profile) {
        ViharaProfile savedProfile = viharaProfileService.saveProfile(profile);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }

    // Endpoint untuk memperbarui profil (hanya Admin yang boleh)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Hanya ROLE_ADMIN yang bisa memperbarui profil
    public ResponseEntity<ViharaProfile> updateProfile(@PathVariable Long id, @Valid @RequestBody ViharaProfile profile) {
        try {
            ViharaProfile updated = viharaProfileService.updateProfile(id, profile);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Atau HttpStatus.BAD_REQUEST
        }
    }

    // Endpoint untuk menghapus profil (hanya Admin yang boleh)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Hanya ROLE_ADMIN yang bisa menghapus profil
    public ResponseEntity<HttpStatus> deleteProfile(@PathVariable Long id) {
        viharaProfileService.deleteProfile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
