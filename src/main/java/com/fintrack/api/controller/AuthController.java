package com.fintrack.api.controller;

import com.fintrack.api.dto.AuthRequest;
import com.fintrack.api.dto.AuthResponse;
import com.fintrack.api.entity.User;
import com.fintrack.api.exception.UsernameAlreadyExistsException;
import com.fintrack.api.repository.UserRepository;
import com.fintrack.api.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = generateTokenForUser(request.getUsername());

        log.info("Login successful for user: {}", request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, request.getUsername()));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        log.info("Registration attempt for user: {}", request.getUsername());

        if (userRepository.findByName(request.getUsername()).isPresent()) {
            log.warn("Registration failed - username already exists: {}", request.getUsername());
            throw new UsernameAlreadyExistsException("Username already taken");
        }

        User user = User.builder()
                .name(request.getUsername())
                .email(request.getUsername() + "@fintrack.local")
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        userRepository.flush();

        log.info("User registered successfully: {}", request.getUsername());

        String token = generateTokenForUser(request.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, request.getUsername()));
    }

    private String generateTokenForUser(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenProvider.generateToken(userDetails);
    }
}
