package com.fintrack.api.mapper;

import com.fintrack.api.dto.TransactionDto;

import com.fintrack.api.entity.Tag;
import com.fintrack.api.entity.Transaction;
import org.springframework.stereotype.Component;


@Component
public class TransactionMapper {

    public TransactionDto toDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());

        if (transaction.getAccount() != null) {
            dto.setAccountId(transaction.getAccount().getId());
        }

        if (transaction.getCategory() != null) {
            dto.setCategoryId(transaction.getCategory().getId());
        }

        if (transaction.getTags() != null) {
            dto.setTagIds(
                    transaction.getTags()
                            .stream()
                            .map(Tag::getId)
                            .toList()
            );
        }

        return dto;
    }

    public Transaction toEntity(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setAmount(dto.getAmount());
        return transaction;
    }
}