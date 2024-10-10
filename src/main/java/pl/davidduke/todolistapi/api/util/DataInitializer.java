package pl.davidduke.todolistapi.api.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.davidduke.todolistapi.api.dto.users.UserCreateDto;
import pl.davidduke.todolistapi.api.services.UserService;
import pl.davidduke.todolistapi.storage.entities.UserEntity;
import pl.davidduke.todolistapi.storage.enums.Role;
import pl.davidduke.todolistapi.storage.repositories.UserRepository;

import java.util.Locale;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(
                UserEntity
                        .builder()
                        .email("admin@gmail.com")
                        .firstName("admin")
                        .lastName("davidduke")
                        .password(passwordEncoder.encode("12345678"))
                        .role(Role.ROLE_ADMIN)
                        .build()
        );
    }
}
