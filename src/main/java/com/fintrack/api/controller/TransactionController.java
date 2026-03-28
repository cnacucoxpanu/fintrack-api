package com.fintrack.api.controller;

import com.fintrack.api.dto.TransactionDto;
import com.fintrack.api.entity.TransactionDirection;
import com.fintrack.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @GetMapping("/search")
    public Page<TransactionDto> search(@RequestParam String userName, Pageable pageable) {
        return service.searchByUserName(userName, pageable);
    }

    @GetMapping("/search-native")
    public Page<TransactionDto> searchNative(@RequestParam String userName, Pageable pageable) {
        return service.searchByUserNameNative(userName, pageable);
    }

    @GetMapping
    public List<TransactionDto> getAllTransactions(
            @RequestParam(required = false) TransactionDirection direction) {
        return service.findAll(direction);
    }

    @PostMapping
    public void createTransaction(@Valid @RequestBody TransactionDto dto) {
        service.saveWithTransactional(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/cache/stats")
    public Map<String, Integer> getCacheStats() {
        return service.getCacheStats();
    }

    @PostMapping("/cache/clear")
    public void clearCache() {
        service.clearCache();
    }

    @PostMapping("/bulk")
    public List<TransactionDto> createTransactionsBulk(@Valid @RequestBody List<TransactionDto> dtos) {
        return service.saveBulk(dtos);
    }

    @PostMapping("/bulk-no-tx")
    public List<TransactionDto> createTransactionsBulkNoTransactional(@Valid @RequestBody List<TransactionDto> dtos) {
        return service.saveBulkWithoutTransactional(dtos);
    }
}