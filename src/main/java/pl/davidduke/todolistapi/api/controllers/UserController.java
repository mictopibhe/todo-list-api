package pl.davidduke.todolistapi.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.davidduke.todolistapi.api.dto.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.UserUpdateDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.security.Principal;
import java.util.Locale;


@RestController
@RequestMapping("${api.endpoint.base-url}")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
public class UserController {

    private static final String ME = "/users/me";
    private static final String CHANGE_PASSWORD = "/password";
    private final UserService userService;

    @GetMapping(ME)
    public ResponseEntity<ResponseUserDto> returnCurrentUser(
            Principal principal,
            Locale locale
    ) {
        return ResponseEntity.ok(
                userService
                        .returnUserByEmail(principal.getName(), locale)
        );
    }

    //todo: add method changePassword
    //todo: Blank
    @PatchMapping(ME)
    public ResponseEntity<ResponseUserDto> updateYourself(
            Principal principal,
            @RequestBody @Valid UserUpdateDto userUpdateDto,
            Locale locale
    ) {
        return ResponseEntity.ok(
                userService
                        .updateUserByEmail(
                                principal.getName(),
                                userUpdateDto,
                                locale
                        )
        );
    }

    @DeleteMapping(ME)
    public ResponseEntity<Void> deleteYourself(
            Principal principal,
            Locale locale
    ) {
        userService.deleteUserByEmail(principal.getName(), locale);
        return ResponseEntity.noContent().build();
    }
}
