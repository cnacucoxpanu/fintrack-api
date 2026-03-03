package com.fintrack.api.service;

import com.fintrack.api.entity.Account;
import com.fintrack.api.repository.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Account create(Account account) {
        return repository.save(account);
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public Account getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public Account update(Long id, Account account) {
        Account existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        existing.setName(account.getName());
        existing.setBalance(account.getBalance());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
