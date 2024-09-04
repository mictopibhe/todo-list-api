package pl.davidduke.todolistapi.api.dto.errors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiErrorDto {
    HttpStatus status;
    LocalDateTime timestamp;
    String message;
    List<SubApiErrorDto> errors;
}
