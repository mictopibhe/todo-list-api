package pl.davidduke.todolistapi.api.dto.errors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubApiErrorDto {
    String field;
    Object rejectedValue;
    String message;
}
