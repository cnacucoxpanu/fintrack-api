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

    @Query("SELECT t FROM Transaction t JOIN t.account a JOIN a.user u WHERE u.name = :userName")
    Page<Transaction> findByUserName(@Param("userName") String userName, Pageable pageable);

    @Query(value = "SELECT t.* FROM transactions t "
            + " JOIN accounts a ON t.account_id = a.id "
            + " JOIN users u ON a.user_id = u.id "
            + " WHERE u.name = :userName",
            countQuery = "SELECT count(*) FROM transactions t "
                    + " JOIN accounts a ON t.account_id = a.id "
                    + " JOIN users u ON a.user_id = u.id "
                    + " WHERE u.name = :userName",
            nativeQuery = true)
    Page<Transaction> findByUsernameNative(@Param("userName") String userName, Pageable pageable);
}