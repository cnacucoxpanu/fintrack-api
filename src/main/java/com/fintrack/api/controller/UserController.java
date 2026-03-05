package com.fintrack.api.controller;

import com.fintrack.api.dto.UserDto;
import com.fintrack.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/with-accounts/naive")
    public List<UserDto> getAllUsersWithAccountsNPlusOne() {
        return service.findAllWithAccountsNPlusOne();
    }

    @GetMapping("/with-accounts/optimized")
    public List<UserDto> getAllUsersWithAccountsOptimized() {
        return service.findAllWithAccountsOptimized();
    }

    @PostMapping("/demo/non-transactional")
    public void createUserWithAccountsNonTransactional() {
        service.createUserWithAccountsNonTransactional();
    }

    @PostMapping("/demo/transactional")
    public void createUserWithAccountsTransactional() {
        service.createUserWithAccountsTransactional();
    }
}