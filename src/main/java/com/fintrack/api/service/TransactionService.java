package com.fintrack.api.service;

import com.fintrack.api.dto.TransactionDto;
import com.fintrack.api.entity.Account;
import com.fintrack.api.entity.Category;
import com.fintrack.api.entity.Tag;
import com.fintrack.api.entity.Transaction;
import com.fintrack.api.entity.TransactionDirection;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.TransactionMapper;
import com.fintrack.api.repository.AccountRepository;
import com.fintrack.api.repository.CategoryRepository;
import com.fintrack.api.repository.TagRepository;
import com.fintrack.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final TransactionMapper mapper;

    public List<TransactionDto> findAll(TransactionDirection direction) {
        if (direction != null) {
            return transactionRepository.findByDirection(direction)
                    .stream()
                    .map(mapper::toDto)
                    .toList();
        }
        return transactionRepository.findAllWithDetails()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public TransactionDto findById(Long id) {
        return mapper.toDto(transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found")));
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    // 🔥 Метод без транзакции
    public void saveWithoutTransactional(TransactionDto dto) {
        processTransaction(dto);
    }

    // 🔥 Метод с транзакцией
    @Transactional
    public void saveWithTransactional(TransactionDto dto) {
        processTransaction(dto);
    }

    // 🔥 Общая логика
    private void processTransaction(TransactionDto dto) {

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Set<Tag> tags = Set.copyOf(tagRepository.findAllById(dto.getTagIds()));

        Transaction transaction = mapper.toEntity(dto, account, category, tags);
        transaction.setCreatedAt(OffsetDateTime.now());

        transactionRepository.save(transaction);

        BigDecimal newBalance = dto.getDirection() == TransactionDirection.EXPENSE
                ? account.getBalance().subtract(dto.getAmount())
                : account.getBalance().add(dto.getAmount());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new EntityNotFoundException("Balance cannot be negative");
        }

        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}