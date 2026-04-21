package com.fintrack.api.controller;

import com.fintrack.api.dto.AuthRequest;
import com.fintrack.api.dto.AuthResponse;
import com.fintrack.api.entity.User;
import com.fintrack.api.repository.UserRepository;
import com.fintrack.api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = generateTokenForUser(request.getUsername());

        log.info("Login successful for user: {}", request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, request.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        log.info("Registration attempt for user: {}", request.getUsername());

        if (userRepository.findByName(request.getUsername()).isPresent()) {
            log.warn("Registration failed - username already exists: {}", request.getUsername());
            throw new IllegalArgumentException("Username already taken");
        }

        User user = new User();
        user.setName(request.getUsername());
        user.setEmail(request.getUsername() + "@fintrack.local");
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        log.info("User registered successfully: {}", request.getUsername());

        String token = generateTokenForUser(request.getUsername());

        return ResponseEntity.status(201).body(new AuthResponse(token, request.getUsername()));
    }

    private String generateTokenForUser(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenProvider.generateToken(userDetails);
    }
}
