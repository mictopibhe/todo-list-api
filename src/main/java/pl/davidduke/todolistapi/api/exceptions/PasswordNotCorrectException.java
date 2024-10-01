package pl.davidduke.todolistapi.api.exceptions;

public class PasswordNotCorrectException extends RuntimeException {
    public PasswordNotCorrectException(String message) {
        super(message);
    }
}
