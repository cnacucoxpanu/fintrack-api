package com.fintrack.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionDto {
    private Long id;
    private Double amount;
    private Long accountId;
    private Long categoryId;
    private List<Long> tagIds;
}