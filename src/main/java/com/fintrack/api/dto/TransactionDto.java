package com.fintrack.api.dto;

import com.fintrack.api.entity.TransactionDirection;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private Long id;

    @NotNull(message = "Amount is required")
    @Positive(message = "Transaction amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Direction (INCOME/EXPENSE) is required")
    private TransactionDirection direction;

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private Set<Long> tagIds;
}