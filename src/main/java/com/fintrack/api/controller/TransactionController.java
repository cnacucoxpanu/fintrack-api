package com.fintrack.api.controller;

import com.fintrack.api.dto.TransactionDto;
import com.fintrack.api.entity.Transaction;
import com.fintrack.api.mapper.TransactionMapper;
import com.fintrack.api.service.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for transaction resources.
 * Handles HTTP requests for creating, retrieving, and deleting transactions.
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;
    private final TransactionMapper mapper;

    /**
     * Creates a new transaction.
     *
     * @param dto the transaction data transfer object
     * @return the created transaction as a DTO
     */
    @PostMapping
    public TransactionDto create(@Valid @RequestBody TransactionDto dto) {
        // 1. Convert DTO to Entity
        Transaction entity = mapper.toEntity(dto);

        // 2. Call Service to save Entity
        // (Note: In a full implementation, the Service should handle
        // linking Account and Category IDs to actual objects)
        Transaction savedEntity = service.create(entity);

        // 3. Convert saved Entity back to DTO
        return mapper.toDto(savedEntity);
    }

    /**
     * Retrieves all transactions.
     *
     * @return a list of transaction DTOs
     */
    @GetMapping
    public List<TransactionDto> getAll() {
        return service.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id the transaction ID
     * @return the found transaction DTO
     */
    @GetMapping("/{id}")
    public TransactionDto getById(@PathVariable Long id) {
        Transaction entity = service.getById(id);
        return mapper.toDto(entity);
    }

    /**
     * Deletes a transaction by its ID.
     *
     * @param id the transaction ID
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.delete(id);
    }
}