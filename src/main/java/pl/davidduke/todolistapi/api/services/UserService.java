package pl.davidduke.todolistapi.api.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.davidduke.todolistapi.api.dto.UserCreateDto;
import pl.davidduke.todolistapi.api.dto.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.UserListDto;
import pl.davidduke.todolistapi.api.exceptions.EmailIsAlreadyUseException;
import pl.davidduke.todolistapi.api.exceptions.UserNotFoundException;
import pl.davidduke.todolistapi.api.util.UserMapper;
import pl.davidduke.todolistapi.storage.entities.UserEntity;
import pl.davidduke.todolistapi.storage.repositories.UserRepository;

import java.util.Locale;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    final UserRepository userRepository;
    final UserMapper mapper;
    final MessageSource messageSource;

    public UserListDto<ResponseUserDto> fetchAllUsers(
            Pageable pageable
    ) {

        Page<ResponseUserDto> users = userRepository
                .findAll(pageable)
                .map(mapper::userEntityToResponseUserDto);

        return UserListDto
                .<ResponseUserDto>builder()
                .content(users.getContent())
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .pageNumber(users.getNumber())
                .pageSize(users.getSize())
                .build();
    }

    public ResponseUserDto fetchUserById(
            Long id,
            Locale locale
    ) {
        return userRepository
                .findById(id)
                .map(mapper::userEntityToResponseUserDto)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                id,
                                locale,
                                messageSource
                        )
                );
    }

    @Transactional
    public ResponseUserDto createUser(
            UserCreateDto userToBeCreated,
            Locale locale
    ) {
        String newEmail = userToBeCreated.getEmail();

        if (isEmailAlreadyUse(newEmail)) {
            throw new EmailIsAlreadyUseException(
                    newEmail,
                    locale,
                    messageSource
            );
        }
        UserEntity userToBeSaved = mapper
                .userCreateDtoToUserEntity(userToBeCreated);

        return mapper
                .userEntityToResponseUserDto(
                        userRepository
                                .save(userToBeSaved)
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
