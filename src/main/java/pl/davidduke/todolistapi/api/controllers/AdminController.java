package pl.davidduke.todolistapi.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.davidduke.todolistapi.api.dto.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.UserUpdateDto;
import pl.davidduke.todolistapi.api.dto.UsersPageDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.util.Locale;

@RestController
@RequestMapping("${api.endpoint.base-url}")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController {

    private static final String FETCH_USERS = "/admin/users";
    private static final String FETCH_UPDATE_DELETE_USER_BY_ID = "/admin/users/{id}";

    private final UserService userService;

    @GetMapping(FETCH_USERS)
    public ResponseEntity<UsersPageDto<ResponseUserDto>> fetchUsers(
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(
                userService
                        .fetchUsers(pageable)
        );
    }

    @GetMapping(FETCH_UPDATE_DELETE_USER_BY_ID)
    public ResponseEntity<ResponseUserDto> fetchUserById(
            @PathVariable long id, Locale locale
    ) {
        return ResponseEntity.ok(
                userService
                        .returnUserById(id, locale)
        );
    }

    @PatchMapping(FETCH_UPDATE_DELETE_USER_BY_ID)
    public ResponseEntity<ResponseUserDto> updateUserById(
            @PathVariable long id, Locale locale,
            @RequestBody @Valid UserUpdateDto userUpdateDto
    ) {
        return ResponseEntity.ok(
                userService
                        .updateUserById(
                                id,
                                userUpdateDto,
                                locale
                        )
        );
    }

    @DeleteMapping(FETCH_UPDATE_DELETE_USER_BY_ID)
    public ResponseEntity<Void> deleteUserById(
            @PathVariable long id, Locale locale
    ) {
        userService.deleteUserById(id, locale);
        return ResponseEntity.noContent().build();
    }
}
