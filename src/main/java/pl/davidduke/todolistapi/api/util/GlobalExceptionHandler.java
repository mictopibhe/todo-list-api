package pl.davidduke.todolistapi.api.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.davidduke.todolistapi.api.dto.errors.ApiErrorDto;
import pl.davidduke.todolistapi.api.dto.errors.SubApiErrorDto;
import pl.davidduke.todolistapi.api.exceptions.EmailAlreadyUseException;
import pl.davidduke.todolistapi.api.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyUseException.class)
    public ResponseEntity<ApiErrorDto> handleEmailAlreadyUseException(
            EmailAlreadyUseException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ApiErrorDto
                                .builder()
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDto> userNotFoundException(
            UserNotFoundException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiErrorDto
                                .builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND)
                                .message(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorDto> handleValidationException(
            MethodArgumentNotValidException e
    ) {
        List<SubApiErrorDto> subErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(
                        error -> SubApiErrorDto
                                .builder()
                                .field(error.getField())
                                .rejectedValue(error.getRejectedValue())
                                .message(error.getDefaultMessage())
                                .build()
                ).toList();

        return ResponseEntity
                .badRequest()
                .body(
                        ApiErrorDto
                                .builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST)
                                .message("Validation failed")
                                .errors(subErrors)
                                .build()
                );
    }
}
