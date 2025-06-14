package com.vihara.profile.repository;

import com.vihara.profile.model.ViharaProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViharaProfileRepository extends JpaRepository<ViharaProfile, Long> {
    // Anda bisa menambahkan custom query methods di sini jika diperlukan,
    // misalnya untuk mencari profil berdasarkan nama
    Optional<ViharaProfile> findByName(String name);
}