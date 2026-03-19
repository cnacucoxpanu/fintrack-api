package com.fintrack.api.repository;

import com.fintrack.api.entity.Transaction;
import com.fintrack.api.entity.TransactionDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDirection(TransactionDirection direction);

    @Query("SELECT t FROM Transaction t")
    @EntityGraph(attributePaths = {"account", "category", "tags"})
    List<Transaction> findAllWithDetails();

    @Query("SELECT DISTINCT t FROM Transaction t JOIN FETCH t.account a JOIN FETCH a.user u JOIN FETCH t.category WHERE u.name = :userName")
    Page<Transaction> findByUserName(@Param("userName") String userName, Pageable pageable);

    @Query(value = "SELECT t.* FROM transactions t "
            + " JOIN accounts a ON t.account_id = a.id "
            + " JOIN users u ON a.user_id = u.id "
            + " WHERE u.name = :userName",
            nativeQuery = true)
    Page<Transaction> findByUsernameNative(@Param("userName") String userName, Pageable pageable);

    @Query(value = "SELECT t.* FROM transactions t WHERE t.account_id = :accountId "
            + "ORDER BY t.created_at DESC LIMIT :limit",
            nativeQuery = true)
    List<Transaction> findRecentTransactionsByAccountId(@Param("accountId") Long accountId,
                                                        @Param("limit") Integer limit);

    @Query("SELECT t FROM Transaction t JOIN t.category c "
            + "WHERE c.name LIKE %:categoryName%")
    Page<Transaction> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    @Query(value = "SELECT t.* FROM transactions t "
            + "JOIN categories c ON t.category_id = c.id "
            + "WHERE c.name ILIKE %:categoryName% "
            + "ORDER BY t.created_at DESC",
            nativeQuery = true)
    Page<Transaction> findByCategoryNameNative(@Param("categoryName") String categoryName, Pageable pageable);
}