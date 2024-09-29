package pl.davidduke.todolistapi.api.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.davidduke.todolistapi.api.dto.UserCreateDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.util.Locale;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        UserCreateDto admin = UserCreateDto
                .builder()
                .email("admin@gmail.com")
                .firstName("admin")
                .lastName("davidduke")
                .password("12345678")
                .confirmPassword("12345678")
                .build();

        userService.register(admin, Locale.ENGLISH);
    }
}
