package com.fintrack.api.mapper;

import com.fintrack.api.dto.TransactionDto;
import com.fintrack.api.entity.Account;
import com.fintrack.api.entity.Category;
import com.fintrack.api.entity.Tag;
import com.fintrack.api.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TransactionMapper {

    public TransactionDto toDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .direction(transaction.getDirection())
                .accountId(transaction.getAccount() != null ? transaction.getAccount().getId() : null)
                .categoryId(transaction.getCategory() != null ? transaction.getCategory().getId() : null)
                .tagIds(transaction.getTags() != null
                        ? transaction.getTags().stream().map(Tag::getId).collect(java.util.stream.Collectors.toSet())
                        : null)
                .build();
    }

    public Transaction toEntity(TransactionDto dto, Account account, Category category, Set<Tag> tags) {
        return Transaction.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .direction(dto.getDirection())
                .account(account)
                .category(category)
                .tags(tags)
                .build();
    }
}