package pl.davidduke.todolistapi.api.exceptions;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordNotMatchedException extends RuntimeException {
    String message;

    public PasswordNotMatchedException() {
    }

    public PasswordNotMatchedException(String message) {
        super(message);
        this.message = message;
    }
}
