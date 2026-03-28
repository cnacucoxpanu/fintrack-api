package com.fintrack.api.service;

import com.fintrack.api.dto.SearchKey;
import com.fintrack.api.dto.TransactionDto;
import com.fintrack.api.entity.Account;
import com.fintrack.api.entity.Category;
import com.fintrack.api.entity.Tag;
import com.fintrack.api.entity.Transaction;
import com.fintrack.api.entity.TransactionDirection;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.TransactionMapper;
import com.fintrack.api.repository.AccountRepository;
import com.fintrack.api.repository.CategoryRepository;
import com.fintrack.api.repository.TagRepository;
import com.fintrack.api.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TransactionMapper mapper;

    @Mock
    private Map<SearchKey, Page<TransactionDto>> cache;

    @InjectMocks
    private TransactionService service;

    private Transaction transaction;
    private TransactionDto dto;
    private Account account;
    private Category category;
    private Tag tag;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id(1L)
                .name("Test Account")
                .balance(new BigDecimal("1000.00"))
                .build();

        category = Category.builder()
                .id(1L)
                .name("Food")
                .type("EXPENSE")
                .build();

        tag = Tag.builder()
                .id(1L)
                .name("Groceries")
                .build();

        transaction = Transaction.builder()
                .id(1L)
                .amount(new BigDecimal("100.00"))
                .direction(TransactionDirection.EXPENSE)
                .account(account)
                .category(category)
                .tags(Set.of(tag))
                .createdAt(OffsetDateTime.now())
                .build();

        dto = TransactionDto.builder()
                .id(1L)
                .amount(new BigDecimal("100.00"))
                .direction(TransactionDirection.EXPENSE)
                .accountId(1L)
                .categoryId(1L)
                .tagIds(Set.of(1L))
                .build();
    }

    @Nested
    class Search {

        @Test
        void shouldSearchByUserName() {
            Page<Transaction> transactionPage = new PageImpl<>(List.of(transaction));
            Page<TransactionDto> dtoPage = new PageImpl<>(List.of(dto));

            when(transactionRepository.findByUserName(anyString(), any(Pageable.class))).thenReturn(transactionPage);
            when(mapper.toDto(transaction)).thenReturn(dto);
            doAnswer(invocation -> {
                java.util.function.Function<Object, Page<TransactionDto>> func = invocation.getArgument(1);
                func.apply(invocation.getArgument(0));
                return dtoPage;
            }).when(cache).computeIfAbsent(any(), any());

            Page<TransactionDto> result = service.searchByUserName("testUser", PageRequest.of(0, 10));

            assertNotNull(result);
            assertEquals(1, result.getContent().size());
            verify(transactionRepository).findByUserName("testUser", PageRequest.of(0, 10));
        }

        @Test
        void shouldSearchByUserNameNative() {
            Page<Transaction> transactionPage = new PageImpl<>(List.of(transaction));
            Page<TransactionDto> dtoPage = new PageImpl<>(List.of(dto));

            when(transactionRepository.findByUsernameNative(anyString(), any(Pageable.class))).thenReturn(transactionPage);
            when(mapper.toDto(transaction)).thenReturn(dto);
            doAnswer(invocation -> {
                java.util.function.Function<Object, Page<TransactionDto>> func = invocation.getArgument(1);
                func.apply(invocation.getArgument(0));
                return dtoPage;
            }).when(cache).computeIfAbsent(any(), any());

            Page<TransactionDto> result = service.searchByUserNameNative("testUser", PageRequest.of(0, 10));

            assertNotNull(result);
            assertEquals(1, result.getContent().size());
        }

        @Test
        void shouldFindByCategoryName() {
            Page<Transaction> transactionPage = new PageImpl<>(List.of(transaction));
            when(transactionRepository.findByCategoryName(anyString(), any(Pageable.class))).thenReturn(transactionPage);
            when(mapper.toDto(transaction)).thenReturn(dto);

            Page<TransactionDto> result = service.findByCategoryName("Food", PageRequest.of(0, 10));

            assertNotNull(result);
            assertEquals(1, result.getContent().size());
        }

        @Test
        void shouldFindByCategoryNameNative() {
            Page<Transaction> transactionPage = new PageImpl<>(List.of(transaction));
            when(transactionRepository.findByCategoryNameNative(anyString(), any(Pageable.class))).thenReturn(transactionPage);
            when(mapper.toDto(transaction)).thenReturn(dto);

            Page<TransactionDto> result = service.findByCategoryNameNative("Food", PageRequest.of(0, 10));

            assertNotNull(result);
            assertEquals(1, result.getContent().size());
        }
    }

    @Nested
    class FindAll {

        @Test
        void shouldReturnAllWithDirection() {
            when(transactionRepository.findByDirection(TransactionDirection.EXPENSE)).thenReturn(List.of(transaction));
            when(mapper.toDto(transaction)).thenReturn(dto);

            List<TransactionDto> result = service.findAll(TransactionDirection.EXPENSE);

            assertNotNull(result);
            assertEquals(1, result.size());
            verify(transactionRepository).findByDirection(TransactionDirection.EXPENSE);
        }

        @Test
        void shouldReturnAllWithoutDirection() {
            when(transactionRepository.findAllWithDetails()).thenReturn(List.of(transaction));
            when(mapper.toDto(transaction)).thenReturn(dto);

            List<TransactionDto> result = service.findAll(null);

            assertNotNull(result);
            assertEquals(1, result.size());
            verify(transactionRepository).findAllWithDetails();
        }

        @Test
        void shouldFindRecentTransactionsByAccountId() {
            when(transactionRepository.findRecentTransactionsByAccountId(1L, 5)).thenReturn(List.of(transaction));
            when(mapper.toDto(transaction)).thenReturn(dto);

            List<TransactionDto> result = service.findRecentTransactionsByAccountId(1L, 5);

            assertNotNull(result);
            assertEquals(1, result.size());
            verify(transactionRepository).findRecentTransactionsByAccountId(1L, 5);
        }
    }

    @Nested
    class FindById {

        @Test
        void shouldReturnTransaction() {
            when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
            when(mapper.toDto(transaction)).thenReturn(dto);

            TransactionDto result = service.findById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
            verify(transactionRepository).findById(1L);
        }

        @Test
        void shouldThrowWhenNotFound() {
            when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> service.findById(999L));
        }
    }

    @Nested
    class Delete {

        @Test
        void shouldDeleteTransaction() {
            service.delete(1L);

            verify(transactionRepository).deleteById(1L);
            verify(cache).clear();
        }
    }

    @Nested
    class SaveSingle {

        @Test
        void shouldSaveWithTransactional() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(dto, account, category, Set.of(tag))).thenReturn(transaction);
            when(transactionRepository.save(transaction)).thenReturn(transaction);
            when(accountRepository.save(account)).thenReturn(account);

            service.saveWithTransactional(dto);

            verify(transactionRepository).save(transaction);
            verify(accountRepository).save(account);
        }

        @Test
        void shouldSaveWithoutTransactional() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(dto, account, category, Set.of(tag))).thenReturn(transaction);
            when(transactionRepository.save(transaction)).thenReturn(transaction);
            when(accountRepository.save(account)).thenReturn(account);

            service.saveWithoutTransactional(dto);

            verify(transactionRepository).save(transaction);
            verify(accountRepository).save(account);
        }

        @Test
        void shouldThrowWhenAccountNotFound() {
            when(accountRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> service.saveWithTransactional(dto));
        }

        @Test
        void shouldThrowWhenCategoryNotFound() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> service.saveWithTransactional(dto));
        }

        @Test
        void shouldThrowWhenNegativeBalance() {
            account.setBalance(new BigDecimal("50.00"));
            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(dto, account, category, Set.of(tag))).thenReturn(transaction);

            assertThrows(EntityNotFoundException.class, () -> service.saveWithTransactional(dto));
        }

        @Test
        void shouldUpdateBalanceForIncome() {
            TransactionDto incomeDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.INCOME)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of(1L))
                    .build();

            Transaction incomeTransaction = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.INCOME)
                    .account(account)
                    .category(category)
                    .tags(Set.of(tag))
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(incomeDto, account, category, Set.of(tag))).thenReturn(incomeTransaction);
            when(transactionRepository.save(incomeTransaction)).thenReturn(incomeTransaction);
            when(accountRepository.save(account)).thenReturn(account);

            service.saveWithTransactional(incomeDto);

            verify(accountRepository).save(account);
            assertEquals(new BigDecimal("1100.00"), account.getBalance());
        }

        @Test
        void shouldUpdateBalanceForExpense() {
            TransactionDto expenseDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of(1L))
                    .build();

            Transaction expenseTransaction = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of(tag))
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(expenseDto, account, category, Set.of(tag))).thenReturn(expenseTransaction);
            when(transactionRepository.save(expenseTransaction)).thenReturn(expenseTransaction);
            when(accountRepository.save(account)).thenReturn(account);

            service.saveWithTransactional(expenseDto);

            verify(accountRepository).save(account);
            assertEquals(new BigDecimal("900.00"), account.getBalance());
        }

        @Test
        void shouldSaveWithoutTransactionalWithExpense() {
            TransactionDto expenseDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of(1L))
                    .build();

            Transaction expenseTransaction = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of(tag))
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(expenseDto, account, category, Set.of(tag))).thenReturn(expenseTransaction);
            when(transactionRepository.save(expenseTransaction)).thenReturn(expenseTransaction);
            when(accountRepository.save(account)).thenReturn(account);

            service.saveWithoutTransactional(expenseDto);

            verify(accountRepository).save(account);
            assertEquals(new BigDecimal("900.00"), account.getBalance());
        }

        @Test
        void shouldSaveWithoutTransactionalWithNullTagIds() {
            TransactionDto dtoWithNullTags = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(null)
                    .build();

            Transaction transactionWithNullTags = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of())
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(mapper.toEntity(dtoWithNullTags, account, category, Set.of())).thenReturn(transactionWithNullTags);
            when(transactionRepository.save(transactionWithNullTags)).thenReturn(transactionWithNullTags);
            when(accountRepository.save(account)).thenReturn(account);

            service.saveWithoutTransactional(dtoWithNullTags);

            verify(transactionRepository).save(transactionWithNullTags);
            verify(accountRepository).save(account);
        }
    }

    @Nested
    class SaveBulk {

        @Test
        void shouldSaveAllTransactions() {
            TransactionDto dto1 = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of())
                    .build();
            TransactionDto dto2 = TransactionDto.builder()
                    .id(2L)
                    .amount(new BigDecimal("50.00"))
                    .direction(TransactionDirection.INCOME)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of())
                    .build();

            Transaction transaction1 = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of())
                    .build();
            Transaction transaction2 = Transaction.builder()
                    .id(2L)
                    .amount(new BigDecimal("50.00"))
                    .direction(TransactionDirection.INCOME)
                    .account(account)
                    .category(category)
                    .tags(Set.of())
                    .build();

            TransactionDto resultDto1 = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();
            TransactionDto resultDto2 = TransactionDto.builder()
                    .id(2L)
                    .amount(new BigDecimal("50.00"))
                    .direction(TransactionDirection.INCOME)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(mapper.toEntity(dto1, account, category, Set.of())).thenReturn(transaction1);
            when(mapper.toEntity(dto2, account, category, Set.of())).thenReturn(transaction2);
            when(transactionRepository.save(transaction1)).thenReturn(transaction1);
            when(transactionRepository.save(transaction2)).thenReturn(transaction2);
            when(accountRepository.save(account)).thenReturn(account);
            when(mapper.toDto(transaction1)).thenReturn(resultDto1);
            when(mapper.toDto(transaction2)).thenReturn(resultDto2);

            List<TransactionDto> result = service.saveBulk(List.of(dto1, dto2));

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(transactionRepository, times(2)).save(any(Transaction.class));
            verify(accountRepository, times(2)).save(account);
        }

        @Test
        void shouldReturnEmptyListWhenNull() {
            List<TransactionDto> result = service.saveBulk(null);

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldReturnEmptyListWhenEmpty() {
            List<TransactionDto> result = service.saveBulk(List.of());

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldThrowWhenAccountNotFound() {
            TransactionDto invalidDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(999L)
                    .categoryId(1L)
                    .tagIds(Set.of())
                    .build();

            when(accountRepository.findById(999L)).thenReturn(Optional.empty());

            List<TransactionDto> invalidList = List.of(invalidDto);
            assertThrows(EntityNotFoundException.class, () -> service.saveBulk(invalidList));
        }

        @Test
        void shouldThrowWhenCategoryNotFound() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

            List<TransactionDto> dtoList = List.of(dto);
            assertThrows(EntityNotFoundException.class, () -> service.saveBulk(dtoList));
        }

        @Test
        void shouldThrowWhenNegativeBalance() {
            account.setBalance(new BigDecimal("50.00"));
            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(dto, account, category, Set.of(tag))).thenReturn(transaction);

            List<TransactionDto> dtoList = List.of(dto);
            assertThrows(EntityNotFoundException.class, () -> service.saveBulk(dtoList));
        }

        @Test
        void shouldHandleNullTagIds() {
            TransactionDto dtoWithNullTags = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(null)
                    .build();

            Transaction transactionWithNullTags = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of())
                    .build();

            TransactionDto resultDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(mapper.toEntity(dtoWithNullTags, account, category, Set.of())).thenReturn(transactionWithNullTags);
            when(transactionRepository.save(transactionWithNullTags)).thenReturn(transactionWithNullTags);
            when(accountRepository.save(account)).thenReturn(account);
            when(mapper.toDto(transactionWithNullTags)).thenReturn(resultDto);

            List<TransactionDto> result = service.saveBulk(List.of(dtoWithNullTags));

            assertNotNull(result);
            assertEquals(1, result.size());
        }

        @Test
        void shouldHandleEmptyTagIds() {
            TransactionDto dtoWithoutTags = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of())
                    .build();

            Transaction transactionWithoutTags = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of())
                    .build();

            TransactionDto resultDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(mapper.toEntity(dtoWithoutTags, account, category, Set.of())).thenReturn(transactionWithoutTags);
            when(transactionRepository.save(transactionWithoutTags)).thenReturn(transactionWithoutTags);
            when(accountRepository.save(account)).thenReturn(account);
            when(mapper.toDto(transactionWithoutTags)).thenReturn(resultDto);

            List<TransactionDto> result = service.saveBulk(List.of(dtoWithoutTags));

            assertNotNull(result);
            assertEquals(1, result.size());
        }

        @Test
        void shouldUpdateBalanceForIncome() {
            TransactionDto incomeDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.INCOME)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of(1L))
                    .build();

            Transaction incomeTransaction = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.INCOME)
                    .account(account)
                    .category(category)
                    .tags(Set.of(tag))
                    .build();

            TransactionDto resultDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.INCOME)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(incomeDto, account, category, Set.of(tag))).thenReturn(incomeTransaction);
            when(transactionRepository.save(incomeTransaction)).thenReturn(incomeTransaction);
            when(accountRepository.save(account)).thenReturn(account);
            when(mapper.toDto(incomeTransaction)).thenReturn(resultDto);

            List<TransactionDto> result = service.saveBulk(List.of(incomeDto));

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(new BigDecimal("1100.00"), account.getBalance());
        }

        @Test
        void shouldUpdateBalanceForExpense() {
            TransactionDto expenseDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of(1L))
                    .build();

            Transaction expenseTransaction = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of(tag))
                    .build();

            TransactionDto resultDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(expenseDto, account, category, Set.of(tag))).thenReturn(expenseTransaction);
            when(transactionRepository.save(expenseTransaction)).thenReturn(expenseTransaction);
            when(accountRepository.save(account)).thenReturn(account);
            when(mapper.toDto(expenseTransaction)).thenReturn(resultDto);

            List<TransactionDto> result = service.saveBulk(List.of(expenseDto));

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(new BigDecimal("900.00"), account.getBalance());
        }
    }

    @Nested
    class SaveBulkWithoutTransactional {

        @Test
        void shouldSaveAllTransactions() {
            TransactionDto dto1 = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of())
                    .build();

            Transaction transaction1 = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of())
                    .build();

            TransactionDto resultDto1 = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(mapper.toEntity(dto1, account, category, Set.of())).thenReturn(transaction1);
            when(transactionRepository.save(transaction1)).thenReturn(transaction1);
            when(accountRepository.save(account)).thenReturn(account);
            when(mapper.toDto(transaction1)).thenReturn(resultDto1);

            List<TransactionDto> result = service.saveBulkWithoutTransactional(List.of(dto1));

            assertNotNull(result);
            assertEquals(1, result.size());
        }

        @Test
        void shouldReturnEmptyListWhenNull() {
            List<TransactionDto> result = service.saveBulkWithoutTransactional(null);

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldReturnEmptyListWhenEmpty() {
            List<TransactionDto> result = service.saveBulkWithoutTransactional(List.of());

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldThrowWhenAccountNotFound() {
            TransactionDto invalidDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(999L)
                    .categoryId(1L)
                    .tagIds(Set.of())
                    .build();

            when(accountRepository.findById(999L)).thenReturn(Optional.empty());

            List<TransactionDto> invalidList = List.of(invalidDto);
            assertThrows(EntityNotFoundException.class, () -> service.saveBulkWithoutTransactional(invalidList));
        }

        @Test
        void shouldThrowWhenCategoryNotFound() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

            List<TransactionDto> dtoList = List.of(dto);
            assertThrows(EntityNotFoundException.class, () -> service.saveBulkWithoutTransactional(dtoList));
        }

        @Test
        void shouldThrowWhenNegativeBalance() {
            account.setBalance(new BigDecimal("50.00"));
            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(dto, account, category, Set.of(tag))).thenReturn(transaction);

            List<TransactionDto> dtoList = List.of(dto);
            assertThrows(EntityNotFoundException.class, () -> service.saveBulkWithoutTransactional(dtoList));
        }

        @Test
        void shouldHandleNullTagIds() {
            TransactionDto dtoWithNullTags = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(null)
                    .build();

            Transaction transactionWithNullTags = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of())
                    .build();

            TransactionDto resultDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(mapper.toEntity(dtoWithNullTags, account, category, Set.of())).thenReturn(transactionWithNullTags);
            when(transactionRepository.save(transactionWithNullTags)).thenReturn(transactionWithNullTags);
            when(accountRepository.save(account)).thenReturn(account);
            when(mapper.toDto(transactionWithNullTags)).thenReturn(resultDto);

            List<TransactionDto> result = service.saveBulkWithoutTransactional(List.of(dtoWithNullTags));

            assertNotNull(result);
            assertEquals(1, result.size());
        }

        @Test
        void shouldUpdateBalanceForIncome() {
            TransactionDto incomeDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.INCOME)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of(1L))
                    .build();

            Transaction incomeTransaction = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.INCOME)
                    .account(account)
                    .category(category)
                    .tags(Set.of(tag))
                    .build();

            TransactionDto resultDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.INCOME)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(incomeDto, account, category, Set.of(tag))).thenReturn(incomeTransaction);
            when(transactionRepository.save(incomeTransaction)).thenReturn(incomeTransaction);
            when(accountRepository.save(account)).thenReturn(account);
            when(mapper.toDto(incomeTransaction)).thenReturn(resultDto);

            List<TransactionDto> result = service.saveBulkWithoutTransactional(List.of(incomeDto));

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(new BigDecimal("1100.00"), account.getBalance());
        }

        @Test
        void shouldUpdateBalanceForExpense() {
            TransactionDto expenseDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .tagIds(Set.of(1L))
                    .build();

            Transaction expenseTransaction = Transaction.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .account(account)
                    .category(category)
                    .tags(Set.of(tag))
                    .build();

            TransactionDto resultDto = TransactionDto.builder()
                    .id(1L)
                    .amount(new BigDecimal("100.00"))
                    .direction(TransactionDirection.EXPENSE)
                    .accountId(1L)
                    .categoryId(1L)
                    .build();

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(tagRepository.findAllById(Set.of(1L))).thenReturn(List.of(tag));
            when(mapper.toEntity(expenseDto, account, category, Set.of(tag))).thenReturn(expenseTransaction);
            when(transactionRepository.save(expenseTransaction)).thenReturn(expenseTransaction);
            when(accountRepository.save(account)).thenReturn(account);
            when(mapper.toDto(expenseTransaction)).thenReturn(resultDto);

            List<TransactionDto> result = service.saveBulkWithoutTransactional(List.of(expenseDto));

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(new BigDecimal("900.00"), account.getBalance());
        }
    }

    @Nested
    class Cache {

        @Test
        void shouldReturnCacheStats() {
            when(cache.size()).thenReturn(5);
            when(cache.keySet()).thenReturn(Set.of());

            Map<String, Integer> stats = service.getCacheStats();

            assertNotNull(stats);
            assertTrue(stats.containsKey("cacheSize"));
            assertTrue(stats.containsKey("entries"));
        }

        @Test
        void shouldClearCache() {
            service.clearCache();

            verify(cache).clear();
        }
    }
}
