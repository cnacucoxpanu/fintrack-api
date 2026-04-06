package com.fintrack.api.service;

import com.fintrack.api.dto.SearchKey;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final TransactionMapper mapper;
    private final Map<SearchKey, Page<TransactionDto>> cache;

    public Page<TransactionDto> searchByUserName(String userName, Pageable pageable) {
        SearchKey key = new SearchKey(userName, pageable.getPageNumber(), pageable.getPageSize());

        return cache.computeIfAbsent(key, k -> transactionRepository
                .findByUserName(userName, pageable)
                .map(mapper::toDto));
    }

    public List<TransactionDto> findAll(TransactionDirection direction) {
        if (direction != null) {
            return transactionRepository.findByDirection(direction).stream()
                    .map(mapper::toDto)
                    .toList();
        }
        return transactionRepository.findAllWithDetails().stream()
                .map(mapper::toDto)
                .toList();
    }

    public TransactionDto findById(Long id) {
        return transactionRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
    }

    @Transactional
    public void delete(Long id) {
        transactionRepository.deleteById(id);
        invalidateCache();
    }

    public void saveWithoutTransactional(TransactionDto dto) {
        processTransaction(dto);
    }

    @Transactional
    public void saveWithTransactional(TransactionDto dto) {
        processTransaction(dto);
    }

    private void updateAccountBalance(Account account, TransactionDto dto) {
        BigDecimal newBalance = dto.getDirection() == TransactionDirection.EXPENSE
                ? account.getBalance().subtract(dto.getAmount())
                : account.getBalance().add(dto.getAmount());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new EntityNotFoundException("Balance cannot be negative");
        }

        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    private void invalidateCache() {
        cache.clear();
    }

    public List<TransactionDto> findRecentTransactionsByAccountId(Long accountId, Integer limit) {
        return transactionRepository.findRecentTransactionsByAccountId(accountId, limit).stream()
                .map(mapper::toDto)
                .toList();
    }

    public Page<TransactionDto> searchByUserNameNative(String userName, Pageable pageable) {
        SearchKey key = new SearchKey(userName, pageable.getPageNumber(), pageable.getPageSize());

        return cache.computeIfAbsent(key, k -> transactionRepository
                .findByUsernameNative(userName, pageable)
                .map(mapper::toDto));
    }

    public Map<String, Integer> getCacheStats() {
        return Map.of(
                "cacheSize", cache.size(),
                "entries", cache.keySet().stream()
                        .mapToInt(k -> 1)
                        .sum()
        );
    }

    public void clearCache() {
        invalidateCache();
    }

    public Page<TransactionDto> findByCategoryName(String categoryName, Pageable pageable) {
        return transactionRepository.findByCategoryName(categoryName, pageable)
                .map(mapper::toDto);
    }

    public Page<TransactionDto> findByCategoryNameNative(String categoryName, Pageable pageable) {
        return transactionRepository.findByCategoryNameNative(categoryName, pageable)
                .map(mapper::toDto);
    }

    @Transactional
    public List<TransactionDto> saveBulk(List<TransactionDto> dtos) {
        return processBulkTransactions(dtos);
    }
    public List<TransactionDto> saveBulkWithoutTransactional(List<TransactionDto> dtos) {
        return processBulkTransactions(dtos);
    }

    private List<TransactionDto> processBulkTransactions(List<TransactionDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return List.of();
        }

        List<TransactionDto> result = dtos.stream()
                .map(this::processTransactionAndReturnDto)
                .toList();

        invalidateCache();
        return result;
    }

    private void processTransaction(TransactionDto dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Set<Tag> tags = dto.getTagIds() != null
                ? Set.copyOf(tagRepository.findAllById(dto.getTagIds()))
                : Set.of();

        Transaction transaction = mapper.toEntity(dto, account, category, tags);
        transaction.setCreatedAt(OffsetDateTime.now());
        transactionRepository.save(transaction);

        updateAccountBalance(account, dto);
        invalidateCache();
    }

    private TransactionDto processTransactionAndReturnDto(TransactionDto dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Set<Tag> tags = dto.getTagIds() != null
                ? Set.copyOf(tagRepository.findAllById(dto.getTagIds()))
                : Set.of();

        Transaction transaction = mapper.toEntity(dto, account, category, tags);
        transaction.setCreatedAt(OffsetDateTime.now());
        Transaction savedTransaction = transactionRepository.save(transaction);

        updateAccountBalance(account, dto);

        return mapper.toDto(savedTransaction);
    }
}