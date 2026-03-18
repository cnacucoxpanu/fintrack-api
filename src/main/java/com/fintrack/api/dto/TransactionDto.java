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

    @NotNull(message = "Сумма обязательна")
    @Positive(message = "Сумма транзакции должна быть больше нуля")
    private BigDecimal amount;

    @NotNull(message = "Направление (INCOME/EXPENSE) обязательно")
    private TransactionDirection direction;

    @NotNull(message = "ID счета обязателен")
    private Long accountId;

    @NotNull(message = "ID категории обязателен")
    private Long categoryId;

    private Set<Long> tagIds;
}