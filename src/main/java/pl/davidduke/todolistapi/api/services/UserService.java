package pl.davidduke.todolistapi.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.davidduke.todolistapi.api.dto.users.*;
import pl.davidduke.todolistapi.api.exceptions.EmailAlreadyUseException;
import pl.davidduke.todolistapi.api.exceptions.PasswordNotCorrectException;
import pl.davidduke.todolistapi.api.exceptions.UserNotFoundException;
import pl.davidduke.todolistapi.api.util.UserMapper;
import pl.davidduke.todolistapi.storage.entities.UserEntity;
import pl.davidduke.todolistapi.storage.enums.Role;
import pl.davidduke.todolistapi.storage.repositories.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
            UserCreateDto userCreateDto, Locale locale
    ) {
        String newEmail = userCreateDto.getEmail().toLowerCase();
        validateEmailUsage(newEmail, locale);

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        UserEntity newUser = userMapper.toEntity(userCreateDto);

        newUser.setRole(
                newUser.getEmail().equals("admin@gmail.com") ?
                        Role.ROLE_ADMIN :
                        Role.ROLE_USER
        ); // set role admin for my user

        return userMapper.entityToResponseUserDto(userRepository.save(newUser));
    }

    public ResponseUserDto returnUserByEmail(
            String email, Locale locale
    ) {
        UserEntity user = findUserByEmail(email, locale);
        return userMapper.entityToResponseUserDto(user);
    }

    @Transactional
    public ResponseUserDto updateUserByEmail(
            String email, UserUpdateDto userUpdateDto, Locale locale
    ) {
        UserEntity userToBeUpdated = findUserByEmail(email, locale);

        String newEmail = userUpdateDto.getEmail();
        if (newEmail != null && !email.equals(newEmail)) {
            validateEmailUsage(newEmail, locale);
        }
        userMapper.updatePartial(userUpdateDto, userToBeUpdated);
        return userMapper.entityToResponseUserDto(userToBeUpdated);
    }

    @Transactional
    public Map<String, String> updatePassword(
            String email, PasswordDto passwordDto, Locale locale
    ) {
        UserEntity userToBeUpdated = findUserByEmail(email, locale);

        if (passwordEncoder.matches(
                passwordDto.getOldPassword(),
                userToBeUpdated.getPassword())
        ) {
            userToBeUpdated.setPassword(
                    passwordEncoder.encode(passwordDto.getNewPassword())
            );
        } else {
            throw new PasswordNotCorrectException(
                    messageSource.getMessage(
                            "password.not.correct",
                            null,
                            locale
                    )
            );
        }

        return Map.of(
                "message", messageSource.getMessage(
                        "successfully.changed.password",
                        new Object[]{},
                        locale
                )
        );
    }

    @Transactional
    public void deleteUserByEmail(
            String email, Locale locale
    ) {
        userRepository.delete(findUserByEmail(email, locale));
    }

    private void validateEmailUsage(
            String newEmail, Locale locale
    ) {
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

    protected UserEntity findUserByEmail(
            String email, Locale locale
    ) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(
                        messageSource.getMessage(
                                "error.user.notfoundByEmail",
                                new Object[]{email},
                                locale
                        ))
        );
    }

    public UsersPageDto<ResponseUserDto> fetchUsers(
            Pageable pageable
    ) {
        Page<UserEntity> users = userRepository.findAll(pageable);
        List<ResponseUserDto> usersList = users.map(
                userMapper::entityToResponseUserDto
        ).toList();
        return UsersPageDto
                .<ResponseUserDto>builder()
                .content(usersList)
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .pageNumber(pageable.getPageNumber())
                .build();
    }

    public ResponseUserDto returnUserById(
            long id, Locale locale
    ) {
        return userMapper.entityToResponseUserDto(
                userRepository.findById(id).orElseThrow(
                        () -> getUserNotFoundException(id, locale)
                )
        );
    }

    @Transactional
    public ResponseUserDto updateUserById(
            long id, UserUpdateDto userUpdateDto, Locale locale
    ) {
        UserEntity userToBeUpdated = findUserById(id, locale);

        String newEmail = userUpdateDto.getEmail();
        if (newEmail != null && !userToBeUpdated.getEmail().equals(newEmail)) {
            validateEmailUsage(newEmail, locale);
        }
        userMapper.updatePartial(userUpdateDto, userToBeUpdated);
        return userMapper.entityToResponseUserDto(userToBeUpdated);
    }

    @Transactional
    public void deleteUserById(
            long id, Locale locale
    ) {
        userRepository.delete(findUserById(id, locale));
    }

    private UserEntity findUserById(
            long id, Locale locale
    ) {
        return userRepository.findById(id).orElseThrow(
                () -> getUserNotFoundException(id, locale)
        );
    }

    private UserNotFoundException getUserNotFoundException(
            long id, Locale locale
    ) {
        return new UserNotFoundException(
                messageSource.getMessage(
                        "error.user.notfoundById",
                        new Object[]{id},
                        locale
                )
        );
    }
}
