package com.fintrack.api.exception;

import com.fintrack.api.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryInUseException.class)
    public ResponseEntity<ErrorResponse> handleCategoryInUse(CategoryInUseException ex) {
        log.warn("Category deletion failed: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), "CATEGORY_IN_USE", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(TagInUseException.class)
    public ResponseEntity<ErrorResponse> handleTagInUse(TagInUseException ex) {
        log.warn("Tag deletion failed: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), "TAG_IN_USE", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(AccountInUseException.class)
    public ResponseEntity<ErrorResponse> handleAccountInUse(AccountInUseException ex) {
        log.warn("Account deletion failed: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), "ACCOUNT_IN_USE", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
        log.warn("Entity not found: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), "ENTITY_NOT_FOUND", HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameExists(UsernameAlreadyExistsException ex) {
        log.warn("Username already exists: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), "USERNAME_ALREADY_EXISTS", HttpStatus.CONFLICT, null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Authentication failed: Invalid credentials");
        return buildResponse("Invalid username or password", "INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        log.warn("Validation error: {}", details);
        return buildResponse("Validation failed", "VALIDATION_ERROR", HttpStatus.BAD_REQUEST, details);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Parameter '%s' must be of type %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown"
        );
        log.warn("Type mismatch: {}", message);
        return buildResponse(message, "TYPE_MISMATCH", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), "ILLEGAL_ARGUMENT", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
        List<String> details = java.util.Arrays.stream(ex.getDetailMessageArguments())
                .map(Object::toString)
                .toList();
        log.warn("Method validation error: {}", ex.getMessage());
        return buildResponse("Request validation failed", "VALIDATION_ERROR", HttpStatus.BAD_REQUEST, details);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return buildResponse(
                "Internal server error",
                "INTERNAL_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            String message, String code, HttpStatus status, List<String> details) {
        ErrorResponse errorBody = ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .code(code)
                .timestamp(OffsetDateTime.now())
                .details(details)
                .build();

        return new ResponseEntity<>(errorBody, status);
    }
}
