package pl.davidduke.todolistapi.api.exceptions;

public class EmailAlreadyUseException extends RuntimeException {

    public EmailAlreadyUseException() {
    }

    public EmailAlreadyUseException(String message) {
        super(message);
    }
}
