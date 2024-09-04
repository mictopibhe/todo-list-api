package pl.davidduke.todolistapi.api.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ResponseStatus(HttpStatus.IM_USED)
public class EmailIsAlreadyUseException extends RuntimeException {
    public EmailIsAlreadyUseException(
            String email,
            Locale locale,
            MessageSource messageSource
    ) {
        super(
                messageSource.getMessage(
                        "error.email.already.use",
                        new Object[]{email},
                        locale
                )
        );
    }
}
