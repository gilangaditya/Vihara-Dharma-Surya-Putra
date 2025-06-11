// src/main/java/com/vihara/user/repository/RoleRepository.java
package com.vihara.user.repository;

import com.vihara.user.model.ERole;
import com.vihara.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}