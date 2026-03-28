package com.fintrack.api.service;

import com.fintrack.api.dto.AccountDto;
import com.fintrack.api.entity.Account;
import com.fintrack.api.entity.User;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.AccountMapper;
import com.fintrack.api.repository.AccountRepository;
import com.fintrack.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService service;

    private Account account;
    private AccountDto dto;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();

        account = Account.builder()
                .id(1L)
                .name("Test Account")
                .balance(new BigDecimal("1000.00"))
                .user(user)
                .build();

        dto = AccountDto.builder()
                .id(1L)
                .name("Test Account")
                .balance(new BigDecimal("1000.00"))
                .userId(1L)
                .build();
    }

    @Test
    void getAllAccounts_shouldReturnAllAccounts() {
        when(accountRepository.findAll()).thenReturn(List.of(account));
        when(accountMapper.toDto(account)).thenReturn(dto);

        List<AccountDto> result = service.getAllAccounts();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountRepository).findAll();
    }

    @Test
    void getAllAccounts_emptyList_shouldReturnEmptyList() {
        when(accountRepository.findAll()).thenReturn(List.of());

        List<AccountDto> result = service.getAllAccounts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAccountById_shouldReturnAccount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(dto);

        AccountDto result = service.getAccountById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(accountRepository).findById(1L);
    }

    @Test
    void getAccountById_notFound_shouldThrowException() {
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getAccountById(999L));
    }

    @Test
    void saveAccount_shouldSaveAccount() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountMapper.toEntity(dto, user)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(dto);

        AccountDto result = service.saveAccount(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository).findById(1L);
        verify(accountRepository).save(account);
    }

    @Test
    void saveAccount_userNotFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.saveAccount(dto));
    }

    @Test
    void saveAccount_mapperCalled_shouldSaveAccount() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountMapper.toEntity(dto, user)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(dto);

        AccountDto result = service.saveAccount(dto);

        verify(accountMapper).toEntity(dto, user);
        verify(accountMapper).toDto(account);
        assertNotNull(result);
    }

    @Test
    void updateAccount_shouldUpdateAccount() {
        AccountDto updateDto = AccountDto.builder()
                .id(1L)
                .name("Updated Account")
                .balance(new BigDecimal("2000.00"))
                .userId(1L)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(dto);

        AccountDto result = service.updateAccount(1L, updateDto);

        assertNotNull(result);
        verify(accountRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(accountRepository).save(account);
        assertEquals("Updated Account", account.getName());
        assertEquals(new BigDecimal("2000.00"), account.getBalance());
    }

    @Test
    void updateAccount_accountNotFound_shouldThrowException() {
        AccountDto updateDto = AccountDto.builder()
                .id(1L)
                .name("Updated Account")
                .balance(new BigDecimal("2000.00"))
                .userId(1L)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.updateAccount(1L, updateDto));
    }

    @Test
    void updateAccount_userNotFound_shouldThrowException() {
        AccountDto updateDto = AccountDto.builder()
                .id(1L)
                .name("Updated Account")
                .balance(new BigDecimal("2000.00"))
                .userId(1L)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.updateAccount(1L, updateDto));
    }

    @Test
    void deleteAccount_shouldDeleteAccount() {
        when(accountRepository.existsById(1L)).thenReturn(true);
        doNothing().when(accountRepository).deleteById(1L);

        service.deleteAccount(1L);

        verify(accountRepository).existsById(1L);
        verify(accountRepository).deleteById(1L);
    }

    @Test
    void deleteAccount_notFound_shouldThrowException() {
        when(accountRepository.existsById(999L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.deleteAccount(999L));
    }

    @Test
    void getAllAccounts_multipleAccounts_shouldReturnAll() {
        Account account2 = Account.builder()
                .id(2L)
                .name("Savings Account")
                .balance(new BigDecimal("5000.00"))
                .user(user)
                .build();

        AccountDto dto2 = AccountDto.builder()
                .id(2L)
                .name("Savings Account")
                .balance(new BigDecimal("5000.00"))
                .userId(1L)
                .build();

        when(accountRepository.findAll()).thenReturn(List.of(account, account2));
        when(accountMapper.toDto(account)).thenReturn(dto);
        when(accountMapper.toDto(account2)).thenReturn(dto2);

        List<AccountDto> result = service.getAllAccounts();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
