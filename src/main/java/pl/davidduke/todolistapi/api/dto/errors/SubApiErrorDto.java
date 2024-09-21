package pl.davidduke.todolistapi.api.dto.errors;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubApiErrorDto {
    private String field;
    private Object rejectedValue;
    private String message;
}
