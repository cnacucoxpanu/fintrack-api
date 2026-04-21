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
    public ResponseEntity<Object> login(@RequestBody AuthRequest request) {
        try {
            log.info("Login attempt for user: {}", request.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtTokenProvider.generateToken(userDetails);

            log.info("Login successful for user: {}", request.getUsername());
            return ResponseEntity.ok(new AuthResponse(token, request.getUsername()));
        } catch (org.springframework.security.core.AuthenticationException e) {
            log.error("Login failed for user: {}", request.getUsername(), e);
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody AuthRequest request) {
        try {
            log.info("Registration attempt for user: {}", request.getUsername());

            if (userRepository.findByName(request.getUsername()).isPresent()) {
                log.warn("Registration failed - username already exists: {}", request.getUsername());
                return ResponseEntity.status(409).body("Username already taken");
            }

            User user = new User();
            user.setName(request.getUsername());
            user.setEmail(request.getUsername() + "@fintrack.local");
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            userRepository.save(user);
            log.info("User registered successfully: {}", request.getUsername());

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtTokenProvider.generateToken(userDetails);

            return ResponseEntity.status(201).body(new AuthResponse(token, request.getUsername()));
        } catch (org.springframework.dao.DataAccessException e) {
            log.error("Database error during registration for user: {}", request.getUsername(), e);
            return ResponseEntity.status(500).body("Registration failed: database error");
        }
    }
}
