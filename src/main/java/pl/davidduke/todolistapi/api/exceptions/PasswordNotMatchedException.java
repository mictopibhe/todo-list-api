package pl.davidduke.todolistapi.api.exceptions;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class PasswordNotMatchedException extends RuntimeException {
    public PasswordNotMatchedException(Locale locale, MessageSource messageSource) {
        super(
            messageSource.getMessage(
                    () -> new String[] {"error.password.notMatched"},
                    locale
            )
        );
    }
}
