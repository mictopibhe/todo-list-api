package pl.davidduke.todolistapi.api.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.davidduke.todolistapi.api.dto.errors.ApiErrorDto;
import pl.davidduke.todolistapi.api.dto.errors.SubApiErrorDto;
import pl.davidduke.todolistapi.api.exceptions.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorDto> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        List<SubApiErrorDto> subErrors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return SubApiErrorDto.builder()
                                .field(fieldError.getField())
                                .rejectedValue(fieldError.getRejectedValue())
                                .message(fieldError.getDefaultMessage())
                                .build();
                    } else {
                        return SubApiErrorDto.builder()
                                .field("global")
                                .rejectedValue(null)
                                .message(error.getDefaultMessage())
                                .build();
                    }
                })
                .toList();


        return ResponseEntity
                .badRequest()
                .body(getApiErrorDto(
                        HttpStatus.BAD_REQUEST, "Validation failed", subErrors
                ));
    }

    @ExceptionHandler(EmailAlreadyUseException.class)
    public ResponseEntity<ApiErrorDto> handleEmailAlreadyUseException(
            EmailAlreadyUseException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(getApiErrorDto(
                        HttpStatus.CONFLICT, ex.getMessage(), Collections.emptyList()
                ));
    }

    @ExceptionHandler(InvalidTaskStatusException.class)
    public ResponseEntity<ApiErrorDto> handleInvalidTaskStatusException(
            InvalidTaskStatusException ex
    ) {
        return ResponseEntity
                .badRequest()
                .body(getApiErrorDto(
                        HttpStatus.BAD_REQUEST, ex.getMessage(), Collections.emptyList()
                ));
    }

    @ExceptionHandler(PasswordNotCorrectException.class)
    public ResponseEntity<ApiErrorDto> handlePasswordNotCorrectException(
            PasswordNotCorrectException ex
    ) {
        return ResponseEntity
                .badRequest()
                .body(getApiErrorDto(
                        HttpStatus.BAD_REQUEST, ex.getMessage(), Collections.emptyList()
                ));
    }

    @ExceptionHandler(TaskByUserNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleTaskByUserNotFoundException(
            TaskByUserNotFoundException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(getApiErrorDto(
                        HttpStatus.NOT_FOUND, ex.getMessage(), Collections.emptyList()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDto> userNotFoundException(
            UserNotFoundException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(getApiErrorDto(
                        HttpStatus.NOT_FOUND, ex.getMessage(), Collections.emptyList()
                ));
    }

    private ApiErrorDto getApiErrorDto(
            HttpStatus status, String message, List<SubApiErrorDto> subErrors
    ) {
        return ApiErrorDto
                .builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .message(message)
                .errors(subErrors)
                .build();
    }
}
