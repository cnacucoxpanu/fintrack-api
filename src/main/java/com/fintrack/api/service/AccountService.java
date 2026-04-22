package com.fintrack.api.service;

import com.fintrack.api.dto.AccountDto;
import com.fintrack.api.entity.Account;
import com.fintrack.api.entity.User;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.AccountMapper;
import com.fintrack.api.repository.AccountRepository;
import com.fintrack.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .toList();
    }

    public AccountDto getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
    }

    public AccountDto saveAccount(AccountDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

        Account account = accountMapper.toEntity(dto, user);
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Transactional
    public AccountDto updateAccount(Long id, AccountDto dto) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

        existingAccount.setName(dto.getName());
        existingAccount.setBalance(dto.getBalance());
        existingAccount.setUser(user);

        return accountMapper.toDto(accountRepository.save(existingAccount));
    }

    @Transactional
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));

        if (account.getTransactions() != null && !account.getTransactions().isEmpty()) {
            throw new com.fintrack.api.exception.AccountInUseException(
                "Cannot delete account. It has " + account.getTransactions().size() + " transaction(s). " +
                "Please delete those transactions first."
            );
        }

        accountRepository.deleteById(id);
    }
}