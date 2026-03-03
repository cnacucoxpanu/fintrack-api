package com.fintrack.api.service;

import com.fintrack.api.entity.Account;
import com.fintrack.api.entity.User;
import com.fintrack.api.repository.AccountRepository;
import com.fintrack.api.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final AccountRepository accountRepository;

    public User create(User user) {
        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    /**
     * Naive loading of users and their accounts.
     *
     * <p>This method intentionally demonstrates the N+1 select problem:
     * <ul>
     *   <li>First query loads all users.</li>
     *   <li>Then for each user, accessing {@code getAccounts()} triggers an
     *   additional select because of LAZY loading.</li>
     * </ul>
     */
    public List<User> getAllWithAccountsNPlusOne() {
        List<User> users = repository.findAll();
        users.forEach(user -> user.getAccounts().size());
        return users;
    }

    /**
     * Optimized loading of users and their accounts using {@link
     * com.fintrack.api.repository.UserRepository#findAllWithAccounts()} and
     * {@link org.springframework.data.jpa.repository.EntityGraph}.
     *
     * <p>All users and their accounts are loaded with a single query, which
     * removes the N+1 select problem from {@link #getAllWithAccountsNPlusOne()}.
     */
    public List<User> getAllWithAccountsOptimized() {
        return repository.findAllWithAccounts();
    }

    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public User update(Long id, User user) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        existing.setName(user.getName());

        return repository.save(existing);
    }

    @Transactional
    public void createUserWithAccountsTransactional() {
        User user = new User();
        user.setName("Transactional demo user");
        User savedUser = repository.save(user);

        Account account1 = new Account();
        account1.setName("Tx demo account 1");
        account1.setBalance(100.0);
        account1.setUser(savedUser);
        accountRepository.save(account1);

        Account account2 = new Account();
        account2.setName("Tx demo account 2");
        account2.setBalance(200.0);
        account2.setUser(savedUser);
        accountRepository.save(account2);
    }

    public void createUserWithAccountsNonTransactional() {
    }
}
