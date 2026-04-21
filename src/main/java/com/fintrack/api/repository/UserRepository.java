package com.fintrack.api.repository;

import com.fintrack.api.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"accounts"})
    @Query("SELECT u FROM User u")
    List<User> findAllWithAccounts();

    Optional<User> findByName(String name);
}