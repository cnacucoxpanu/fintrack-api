package com.fintrack.api.service;

import com.fintrack.api.entity.Transaction;
import com.fintrack.api.repository.TransactionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    public Transaction getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public Transaction update(Long id, Transaction transaction) {
        Transaction existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        existing.setAmount(transaction.getAmount());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
