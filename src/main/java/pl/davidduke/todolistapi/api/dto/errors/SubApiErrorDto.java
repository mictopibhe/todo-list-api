package pl.davidduke.todolistapi.api.dto.errors;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubApiErrorDto {
    String field;
    Object rejectedValue;
    String message;
}
