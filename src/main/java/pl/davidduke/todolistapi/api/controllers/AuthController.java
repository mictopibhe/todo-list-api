package pl.davidduke.todolistapi.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.davidduke.todolistapi.api.dto.errors.ApiErrorDto;
import pl.davidduke.todolistapi.api.dto.users.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.users.UserCreateDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.util.Locale;

@RestController
@RequestMapping("${api.endpoint.base-url}/auth/signup")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(
            summary = "Registration of the user in the service.",
            description = "Registration a new user in the service and adding him to the database.",
            tags = {"Registration"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User creation data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserCreateDto.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "201",
            description = "User has been successfully created",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseUserDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Incorrect data was entered",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @PostMapping()
    public ResponseEntity<ResponseUserDto> signUp(
            @RequestBody @Valid UserCreateDto userCreateDto,
            @Parameter(description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                    name = "Accept-Language", in = ParameterIn.HEADER) Locale locale
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(userCreateDto, locale));
    }
}
