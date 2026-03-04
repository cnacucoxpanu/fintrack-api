package com.fintrack.api.dto;

import com.fintrack.api.entity.TransactionDirection;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private Long id;
    private BigDecimal amount;
    private TransactionDirection direction;
    private Long accountId;
    private Long categoryId;
    private Set<Long> tagIds;
}