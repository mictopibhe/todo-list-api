package pl.davidduke.todolistapi.api.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(
            Long id,
            Locale locale,
            MessageSource messageSource
    ) {

        super(
                messageSource.getMessage(
                        "error.user.notfound",
                        new Object[]{id},
                        locale
                )
        );
    }
}
