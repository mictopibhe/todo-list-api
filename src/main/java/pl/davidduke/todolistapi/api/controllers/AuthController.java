package pl.davidduke.todolistapi.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthController {
    static final String SIGN_UP = "/signup";

    final UserService userService;

    @GetMapping
    public String hello() {
        return "Hello World!";
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<ResponseUserDto> signUp(
            @RequestBody PostUserDto postUserDto,
            Locale locale
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        userService
                                .postUser(
                                        postUserDto,
                                        locale
                                )
                );
    }
}