package pl.davidduke.todolistapi.api.exceptions;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailAlreadyUseException extends RuntimeException {
    String message;

    public EmailAlreadyUseException() {
    }

    public EmailAlreadyUseException(String message) {
        super(message);
        this.message = message;
    }
}
