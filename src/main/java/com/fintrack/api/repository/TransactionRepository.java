package com.fintrack.api.repository;

import com.fintrack.api.entity.Transaction;
import com.fintrack.api.entity.TransactionDirection;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDirection(TransactionDirection direction);

    @Query("SELECT t FROM Transaction t")
    @EntityGraph(attributePaths = {"account", "category", "tags"})
    List<Transaction> findAllWithDetails();
}