package com.fintrack.api.controller;

import com.fintrack.api.dto.AuthRequest;
import com.fintrack.api.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Полностью имитируем успешный вход без обращения к БД
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(new AuthResponse("fake-token-123", request.getUsername()));
    }

    // Полностью имитируем успешную регистрацию без обращения к БД
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(new AuthResponse("fake-token-123", request.getUsername()));
    }
}