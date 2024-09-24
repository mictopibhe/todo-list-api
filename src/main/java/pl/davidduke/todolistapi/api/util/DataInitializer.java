package pl.davidduke.todolistapi.api.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.davidduke.todolistapi.api.dto.PostUserDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        PostUserDto admin = PostUserDto
                .builder()
                .email("admin@davidduke.com")
                .firstName("admin")
                .lastName("davidduke")
                .password("password")
                .matchingPassword("password")
                .build();
        userService.postUser(admin, Locale.ENGLISH);
    }
}
