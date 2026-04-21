package com.fintrack.api.controller;

import com.fintrack.api.dto.TransactionDto;
import com.fintrack.api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class DemoController {

    private final TransactionService transactionService;

    @GetMapping("/jpql/user")
    public Page<TransactionDto> searchByUserJpql(
            @RequestParam String userName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        Pageable pageable = createPageable(page, size, sort);
        return transactionService.searchByUserName(userName, pageable);
    }

    @GetMapping("/native/user")
    public Page<TransactionDto> searchByUserNative(
            @RequestParam String userName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        Pageable pageable = createPageable(page, size, sort);
        return transactionService.searchByUserNameNative(userName, pageable);
    }

    @GetMapping("/jpql/category")
    public Page<TransactionDto> searchByCategoryJpql(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        Pageable pageable = createPageable(page, size, sort);
        return transactionService.findByCategoryName(categoryName, pageable);
    }

    @GetMapping("/native/category")
    public Page<TransactionDto> searchByCategoryNative(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        Pageable pageable = createPageable(page, size, sort);
        return transactionService.findByCategoryNameNative(categoryName, pageable);
    }

    @GetMapping("/recent/{accountId}")
    public List<TransactionDto> getRecentTransactions(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "5") int limit) {

        return transactionService.findRecentTransactionsByAccountId(accountId, limit);
    }

    @GetMapping("/cache/stats")
    public Map<String, Integer> getCacheStats() {
        return transactionService.getCacheStats();
    }

    @GetMapping("/cache/clear")
    public String clearCache() {
        transactionService.clearCache();
        return "Cache cleared successfully";
    }

    private Pageable createPageable(int page, int size, String sort) {
        return PageRequest.of(page, size, Sort.by(sort.split(",")));
    }
}
