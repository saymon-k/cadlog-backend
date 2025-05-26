package com.example.authsystem.controller;

import com.example.authsystem.dto.LoginDTO;
import com.example.authsystem.dto.UserDTO;
import com.example.authsystem.dto.LoginResponseDTO;
import com.example.authsystem.model.User;
import com.example.authsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            user.setBirthDate(userDTO.getBirthDate());
            user.setCep(userDTO.getCep());
            userRepository.save(user);
            return ResponseEntity.ok(new LoginResponseDTO(user.getUsername(), user.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during registration: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());
            if (user.isPresent() && bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
                return ResponseEntity.ok(new LoginResponseDTO(user.get().getUsername(), user.get().getEmail()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        try {
            Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setUsername(userDTO.getUsername());
                user.setBirthDate(userDTO.getBirthDate());
                user.setCep(userDTO.getCep());
                userRepository.save(user);
                return ResponseEntity.ok(new LoginResponseDTO(user.getUsername(), user.getEmail()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during update: " + e.getMessage());
        }
    }
}