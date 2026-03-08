package com.fintrack.api.controller;

import com.fintrack.api.dto.TransactionDto;
import com.fintrack.api.entity.TransactionDirection;
import com.fintrack.api.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Управление транзакциями")
public class TransactionController {

    private final TransactionService service;

    @Operation(summary = "Поиск по пользователю с кэшированием и пагинацией")
    @GetMapping("/search")
    public Page<TransactionDto> search(@RequestParam String userName, Pageable pageable) {
        return service.searchByUserName(userName, pageable);
    }

    @Operation(summary = "Получить все транзакции с фильтрацией")
    @GetMapping
    public List<TransactionDto> getAllTransactions(
            @RequestParam(required = false) TransactionDirection direction) {
        return service.findAll(direction);
    }

    @Operation(summary = "Создать транзакцию (с управлением транзакциями)")
    @PostMapping
    public void createTransaction(@Valid @RequestBody TransactionDto dto) {
        service.saveWithTransactional(dto);
    }

    @Operation(summary = "Удалить транзакцию")
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        service.delete(id);
    }
}