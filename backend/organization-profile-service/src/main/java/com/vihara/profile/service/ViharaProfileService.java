package com.vihara.profile.service;

import com.vihara.profile.model.ViharaProfile;
import com.vihara.profile.repository.ViharaProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // Mengaktifkan manajemen transaksi
public class ViharaProfileService {

    private final ViharaProfileRepository viharaProfileRepository;

    public ViharaProfileService(ViharaProfileRepository viharaProfileRepository) {
        this.viharaProfileRepository = viharaProfileRepository;
    }

    public List<ViharaProfile> findAllProfiles() {
        return viharaProfileRepository.findAll();
    }

    public Optional<ViharaProfile> findProfileById(Long id) {
        return viharaProfileRepository.findById(id);
    }

    public ViharaProfile saveProfile(ViharaProfile profile) {
        // Logika bisnis tambahan sebelum menyimpan, misalnya validasi
        // Jika hanya ingin ada satu profil utama, Anda bisa menambahkan logika di sini
        // untuk memastikan hanya satu entry yang ada, atau selalu update yang sudah ada.
        // Untuk saat ini, kita izinkan multiple entries, tapi bisa disesuaikan nanti.
        return viharaProfileRepository.save(profile);
    }

    public ViharaProfile updateProfile(Long id, ViharaProfile updatedProfile) {
        return viharaProfileRepository.findById(id).map(profile -> {
            profile.setName(updatedProfile.getName());
            profile.setHistory(updatedProfile.getHistory());
            profile.setContactEmail(updatedProfile.getContactEmail());
            profile.setContactPhone(updatedProfile.getContactPhone());
            profile.setAddress(updatedProfile.getAddress());
            profile.setCity(updatedProfile.getCity());
            profile.setPostalCode(updatedProfile.getPostalCode());
            profile.setImageUrl(updatedProfile.getImageUrl());
            // Update properti lain sesuai kebutuhan
            return viharaProfileRepository.save(profile);
        }).orElseThrow(() -> new RuntimeException("Profile not found with id " + id));
    }

    public void deleteProfile(Long id) {
        viharaProfileRepository.deleteById(id);
    }

    // Metode untuk mencari profil utama (jika Anda hanya ingin satu profil utama)
    public Optional<ViharaProfile> findMainProfile() {
        // Jika Anda hanya menyimpan satu profil, Anda bisa asumsikan id=1 atau mencari yang namanya 'Main Profile'
        // Untuk saat ini, kita akan asumsikan hanya ada satu atau kita ambil yang pertama
        return viharaProfileRepository.findByName("Main Vihara Profile"); // Contoh
        // Atau:
        // return viharaProfileRepository.findById(1L);
    }
}
