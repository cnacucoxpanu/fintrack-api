package com.fintrack.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class ApiApplicationTests {

    @Test
    void contextLoads() {
        // This method is intentionally empty to verify that the Spring Application Context
        // loads successfully. Any configuration or database connection issues will
        // cause this test to fail automatically.
    }

}