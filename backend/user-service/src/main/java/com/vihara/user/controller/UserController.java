package com.vihara.user.controller;

import com.vihara.user.model.User;
import com.vihara.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Path dasar untuk endpoint user
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping // Ini akan menangani GET /api/users
    @PreAuthorize("hasRole('ADMIN')") // Hanya ADMIN yang bisa mengakses
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Anda bisa tambahkan method lain di sini, misalnya:
    // @GetMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @userSecurity.isCurrentUser(#id))")
    // public ResponseEntity<User> getUserById(@PathVariable Long id) {
    //     User user = userRepository.findById(id)
    //             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));
    //     return new ResponseEntity<>(user, HttpStatus.OK);
    // }
}
