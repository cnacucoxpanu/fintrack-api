package com.fintrack.api.repository;

import com.fintrack.api.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Добавь импорт
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Явно указываем запрос, чтобы Spring не гадал по названию метода
    @Query("SELECT u FROM User u")
    @EntityGraph(attributePaths = "accounts")
    List<User> findAllWithAccounts();
}