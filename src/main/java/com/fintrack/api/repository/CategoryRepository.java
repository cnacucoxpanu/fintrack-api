package com.fintrack.api.repository;

import com.fintrack.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Spring Data JPA automatically generates implementation
}