package pl.davidduke.todolistapi.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.davidduke.todolistapi.api.dto.UserCreateDto;
import pl.davidduke.todolistapi.api.dto.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.UserUpdateDto;
import pl.davidduke.todolistapi.api.exceptions.EmailAlreadyUseException;
import pl.davidduke.todolistapi.api.exceptions.UserNotFoundException;
import pl.davidduke.todolistapi.api.util.UserMapper;
import pl.davidduke.todolistapi.storage.entities.UserEntity;
import pl.davidduke.todolistapi.storage.enums.Role;
import pl.davidduke.todolistapi.storage.repositories.UserRepository;

import java.util.Locale;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Transactional
    public ResponseUserDto register(
            UserCreateDto userCreateDto,
            Locale locale
    ) {
        String newEmail = userCreateDto.getEmail().toLowerCase();
        validateEmailUsage(newEmail, locale);

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        System.out.println(userCreateDto.getPassword());

        UserEntity newUser = userMapper.toEntity(userCreateDto);
        System.out.println(newUser.getPassword());
        newUser.setRole(
                newUser.getEmail().equals("admin@gmail.com") ?
                        Role.ROLE_ADMIN :
                        Role.ROLE_USER
        ); // set role admin for my user

        return userMapper.entityToResponseUserDto(userRepository.save(newUser));
    }

    public ResponseUserDto returnUserByEmail(
            String email,
            Locale locale
    ) {
        UserEntity user = findUserByEmail(email, locale);
        return userMapper.entityToResponseUserDto(user);
    }

    @Transactional
    public ResponseUserDto updateUserByEmail(
            String email,
            UserUpdateDto userUpdateDto,
            Locale locale
    ) {
        UserEntity userToBeUpdated = findUserByEmail(email, locale);

        String newEmail = userUpdateDto.getEmail();
        if (newEmail != null && !email.equals(newEmail)) {
            validateEmailUsage(newEmail, locale);
        }
        userMapper.updatePartial(userUpdateDto, userToBeUpdated);
        userRepository.save(userToBeUpdated);
        return userMapper.entityToResponseUserDto(userToBeUpdated);
    }

    @Transactional
    public void deleteUserByEmail(String email, Locale locale) {
        UserEntity user = findUserByEmail(email, locale);
        userRepository.delete(user);
    }

    private void validateEmailUsage(String newEmail, Locale locale) {
        if (isEmailAlreadyUsed(newEmail)) {
            throw new EmailAlreadyUseException(messageSource.getMessage(
                    "error.email.already.use",
                    new Object[]{newEmail},
                    locale
            ));
        }

    }

    private boolean isEmailAlreadyUsed(
            String email
    ) {
        return userRepository
                .findByEmail(email)
                .isPresent();
    }

    private UserEntity findUserByEmail(String email, Locale locale) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(
                        messageSource.getMessage(
                                "error.user.notfoundByEmail",
                                new Object[]{email},
                                locale
                        ))
        );
    }
}
