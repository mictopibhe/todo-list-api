package pl.davidduke.todolistapi.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.davidduke.todolistapi.api.dto.PostUserDto;
import pl.davidduke.todolistapi.api.dto.ResponseUserDto;
import pl.davidduke.todolistapi.api.exceptions.EmailAlreadyUseException;
import pl.davidduke.todolistapi.api.util.UserMapper;
import pl.davidduke.todolistapi.storage.repositories.UserRepository;

import java.util.Locale;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Transactional
    public ResponseUserDto postUser(
            PostUserDto newUser,
            Locale locale
    ) {
        String newEmail = newUser.getEmail();

        if (isEmailAlreadyUse(newEmail)) {
            throw new EmailAlreadyUseException(
                    messageSource.getMessage(
                            "error.email.already.use",
                            new Object[]{newEmail},
                            locale
                    )
            );
        }

        newUser.setPassword(
                passwordEncoder.encode(newUser.getPassword())
        );

        return mapper.userEntityToResponseUserDto(
                userRepository.save(
                        mapper.postUserDtoToUserEntity(newUser)
                )
        );
    }

    private boolean isEmailAlreadyUse(
            String email
    ) {
        return userRepository
                .findByEmail(email)
                .isPresent();
    }
}
