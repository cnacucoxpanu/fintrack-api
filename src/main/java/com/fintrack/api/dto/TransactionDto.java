package com.fintrack.api.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate transactionDate;
    private LocalDate bookingDate;
    private String direction;
    private String merchantName;
    private String location;
    private Boolean planned;
    private Long accountId;
    private Long categoryId;
    private List<Long> tagIds;
}