package com.fintrack.api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, name = "password_hash")
    private String passwordHash;

    @Column(nullable = false, length = 3)
    private String defaultCurrencyCode;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime lastLoginAt;

    //OneToMany
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();
}
