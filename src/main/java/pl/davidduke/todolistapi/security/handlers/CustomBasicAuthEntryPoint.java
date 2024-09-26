package pl.davidduke.todolistapi.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pl.davidduke.todolistapi.api.dto.errors.ApiErrorDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomBasicAuthEntryPoint implements AuthenticationEntryPoint {

    private static final String BAD_CREDENTIALS_ERROR = "error.badCredentials";
    private static final String INSUFFICIENT_AUTH_ERROR = "error.fullAuthRequired";
    private static final String CREDENTIALS_NOT_FOUND_ERROR = "error.authCredentialsNotFound";

    private final MessageSource messageSource;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        String errorMessage = getErrorMessage(authException, request.getLocale());

        ApiErrorDto errorDto = ApiErrorDto
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED)
                .message(errorMessage)
                .errors(Collections.emptyList())
                .build();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(errorDto));
    }

    private String getErrorMessage(AuthenticationException authException, Locale locale) {
        StringBuilder message = new StringBuilder();
        if (authException instanceof BadCredentialsException) {
            message.append(messageSource.getMessage(BAD_CREDENTIALS_ERROR, null, locale));
        } else if (authException instanceof InsufficientAuthenticationException) {
            message.append(messageSource.getMessage(INSUFFICIENT_AUTH_ERROR, null, locale)) ;
        } else if (authException instanceof AuthenticationCredentialsNotFoundException) {
            message.append(messageSource.getMessage(CREDENTIALS_NOT_FOUND_ERROR, null, locale));
        } else {
            message.append(authException.getMessage());
        }
        return message.toString();
    }
}
