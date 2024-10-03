package pl.davidduke.todolistapi.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.davidduke.todolistapi.api.dto.users.UserCreateDto;
import pl.davidduke.todolistapi.api.dto.users.ResponseUserDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.util.Locale;

@RestController
@RequestMapping("${api.endpoint.base-url}/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String SIGN_UP = "/signup";

    private final UserService userService;

    @PostMapping(SIGN_UP)
    public ResponseEntity<ResponseUserDto> signUp(
            @RequestBody @Valid UserCreateDto userCreateDto, Locale locale
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(userCreateDto, locale));
    }
}
