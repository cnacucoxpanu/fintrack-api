package com.fintrack.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        // This method is intentionally empty to verify that the Spring Application Context
        // loads successfully. Any configuration or database connection issues will
        // cause this test to fail automatically.
    }
}