package com.fintrack.api.controller;

import com.fintrack.api.dto.AccountDto;
import com.fintrack.api.dto.UserDto;
import com.fintrack.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return service.getAllAccounts();
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping
    public AccountDto createAccount(@RequestBody AccountDto dto) {
        return service.saveAccount(dto);
    }
}