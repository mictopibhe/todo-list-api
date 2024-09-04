package pl.davidduke.todolistapi.api.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.davidduke.todolistapi.api.dto.UserCreateDto;
import pl.davidduke.todolistapi.api.dto.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.UserListDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.util.Locale;


@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
    static final String FETCH_USERS = "api/v1/users";
    static final String FETCH_USER = "api/v1/users/{id}";
    static final String CREATE_USER = "api/v1/users";
    static final String UPDATE_USER = "api/v1/user/{id}";
    static final String DELETE_USER = "api/v1/user/{id}";

    final UserService userService;

    @GetMapping(FETCH_USERS)
    public ResponseEntity<UserListDto<ResponseUserDto>> fetchUsers(
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity
                .ok(
                        userService
                                .fetchAllUsers(pageable)
                );
    }

    @GetMapping(FETCH_USER)
    public ResponseEntity<ResponseUserDto> fetchUser(
            @PathVariable Long id,
            Locale locale
    ) {
        return ResponseEntity
                .ok
                        (userService
                                .fetchUserById(
                                        id,
                                        locale
                                )
                        );
    }

    @PostMapping(CREATE_USER)
    public ResponseEntity<ResponseUserDto> createUser(
            @RequestBody @Valid UserCreateDto userToBeCreated,
            Locale locale
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        userService
                                .createUser(
                                        userToBeCreated,
                                        locale
                                )
                );
    }

    @PatchMapping(UPDATE_USER)
    public ResponseEntity<ResponseUserDto> updateUser(
            @PathVariable Long id,
            @RequestBody
            ) {

    }

}
