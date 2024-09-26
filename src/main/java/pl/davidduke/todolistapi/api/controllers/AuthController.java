package pl.davidduke.todolistapi.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.davidduke.todolistapi.api.dto.PostUserDto;
import pl.davidduke.todolistapi.api.dto.ResponseUserDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String SIGN_UP = "/signup";

    private final UserService userService;

    @GetMapping
    public String hello () {
        return "Hello World!";
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<ResponseUserDto> signUp(
            @RequestBody @Valid PostUserDto postUserDto,
            Locale locale
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.postUser(postUserDto, locale));
    }
}
