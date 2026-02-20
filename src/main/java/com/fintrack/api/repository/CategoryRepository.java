package com.fintrack.api.repository;

import com.fintrack.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository for accessing category data
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
