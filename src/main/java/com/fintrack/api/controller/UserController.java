package com.fintrack.api.controller;

import com.fintrack.api.dto.UserDto;
import com.fintrack.api.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public UserDto createUser(@RequestBody UserDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return service.findAll();
    }

    /**
     * Demonstrates the N+1 select problem: users are loaded first and then
     * each user's accounts are loaded in separate queries.
     */
    @GetMapping("/with-accounts/naive")
    public List<UserDto> getAllUsersWithAccountsNPlusOne() {
        return service.findAllWithAccountsNPlusOne();
    }

    /**
     * Demonstrates the optimized loading using {@code @EntityGraph} to avoid
     * the N+1 select problem.
     */
    @GetMapping("/with-accounts/optimized")
    public List<UserDto> getAllUsersWithAccountsOptimized() {
        return service.findAllWithAccountsOptimized();
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
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }

    /**
     * Starts a demo without {@code @Transactional}. After the method fails, you
     * will see partially saved data in the database.
     */
    @PostMapping("/demo/non-transactional")
    public void createUserWithAccountsNonTransactional() {
        service.createUserWithAccountsNonTransactional();
    }

    /**
     * Starts a demo with {@code @Transactional}. When the method fails, all
     * changes are rolled back and nothing is saved in the database.
     */
    @PostMapping("/demo/transactional")
    public void createUserWithAccountsTransactional() {
        service.createUserWithAccountsTransactional();
    }
}