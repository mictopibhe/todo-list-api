package pl.davidduke.todolistapi.api.exceptions;

public class TaskByUserNotFoundException extends RuntimeException {
    public TaskByUserNotFoundException(String message) {
        super(message);
    }
}
