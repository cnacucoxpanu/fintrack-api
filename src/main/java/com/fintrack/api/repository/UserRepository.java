package com.fintrack.api.repository;

import com.fintrack.api.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Loads users together with their accounts using an {@link EntityGraph}
     * to avoid the N+1 select problem when accessing the accounts collection.
     */
    @EntityGraph(attributePaths = "accounts")
    List<User> findAllWithAccounts();
}