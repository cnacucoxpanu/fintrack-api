package com.fintrack.api.controller;

import com.fintrack.api.dto.TransactionDto;
import com.fintrack.api.entity.TransactionDirection;
import com.fintrack.api.service.TransactionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @GetMapping
    public List<TransactionDto> getAllTransactions(
            @RequestParam(required = false) TransactionDirection direction) {
        return service.findAll(direction);
    }

    @GetMapping("/{id}")
    public TransactionDto getTransactionById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/no-tx")
    public void createTransactionWithoutTx(@RequestBody TransactionDto dto) {
        service.saveWithoutTransactional(dto);
    }

    @PostMapping
    public void createTransaction(@RequestBody TransactionDto dto) {
        service.saveWithTransactional(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        service.delete(id);
    }
}