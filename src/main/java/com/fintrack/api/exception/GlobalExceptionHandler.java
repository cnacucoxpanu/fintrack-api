package com.fintrack.api.exception;

import com.fintrack.api.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Глобальный обработчик исключений для всего приложения.
 * Перехватывает и стандартизирует обработку ошибок.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработка исключения "сущность не найдена".
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
        log.warn("Сущность не найдена: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), "ENTITY_NOT_FOUND", HttpStatus.NOT_FOUND, null);
    }

    /**
     * Обработка ошибок валидации входных данных.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        log.warn("Ошибка валидации: {}", details);
        return buildResponse("Ошибка валидации", "VALIDATION_ERROR", HttpStatus.BAD_REQUEST, details);
    }

    /**
     * Обработка ошибок несоответствия типа аргумента метода.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Параметр '%s' должен иметь тип %s".formatted(
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "неизвестен"
        );
        log.warn("Несоответствие типа: {}", message);
        return buildResponse(message, "TYPE_MISMATCH", HttpStatus.BAD_REQUEST, null);
    }

    /**
     * Обработка незаконных аргументов метода.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Незаконный аргумент: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), "ILLEGAL_ARGUMENT", HttpStatus.BAD_REQUEST, null);
    }

    /**
     * Обработка общих исключений.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("Неожиданная ошибка: {}", ex.getMessage(), ex);
        return buildResponse(
                "Внутренняя ошибка сервера",
                "INTERNAL_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            String message, String code, HttpStatus status, List<String> details) {
        ErrorResponse errorBody = ErrorResponse.builder()
                .message(message)
                .code(code)
                .timestamp(OffsetDateTime.now())
                .details(details)
                .build();

        return new ResponseEntity<>(errorBody, status);
    }
}