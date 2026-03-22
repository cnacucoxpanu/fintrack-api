package com.fintrack.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI finTrackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FinTrack API")
                        .description("""
                                API для управления финансами пользователей.
                                
                                **Основные возможности:**
                                - Управление пользователями
                                - Управление банковскими счетами
                                - Управление транзакциями (доходы/расходы)
                                - Категории и теги для транзакций
                                - Поиск и фильтрация данных
                                - Пагинация результатов
                                
                                **Аутентификация:** В разработке
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FinTrack Team")
                                .email("support@fintrack.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
