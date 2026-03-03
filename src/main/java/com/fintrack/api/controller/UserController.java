package com.fintrack.api.controller;

import com.fintrack.api.entity.User;
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
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.create(user);
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    /**
     * Demonstrates the N+1 select problem: users are loaded first and then
     * each user's accounts are loaded in separate queries.
     */
    @GetMapping("/with-accounts/naive")
    public List<User> getAllWithAccountsNPlusOne() {
        return service.getAllWithAccountsNPlusOne();
    }

    /**
     * Demonstrates the optimized loading using {@code @EntityGraph} to avoid
     * the N+1 select problem.
     */
    @GetMapping("/with-accounts/optimized")
    public List<User> getAllWithAccountsOptimized() {
        return service.getAllWithAccountsOptimized();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
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