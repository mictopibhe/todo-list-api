package pl.davidduke.todolistapi.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.davidduke.todolistapi.api.dto.errors.ApiErrorDto;
import pl.davidduke.todolistapi.api.dto.users.PasswordDto;
import pl.davidduke.todolistapi.api.dto.users.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.users.UserUpdateDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.security.Principal;
import java.util.Locale;
import java.util.Map;


@RestController
@RequestMapping("${api.endpoint.base-url}")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
@SecurityRequirement(name = "httpBasic")
public class UserController {

    private static final String ME = "/users/me";
    private static final String CHANGE_PASSWORD = "/users/me/password";
    private final UserService userService;

    @Operation(
            summary = "Obtaining user information",
            description = "Allows an authenticated user to get own profile with its information",
            parameters = {
                    @Parameter(
                            name = "Accept-Language",
                            description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                            schema = @Schema(type = "string", example = "uk")
                    )
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "User has been successfully created",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseUserDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "401",
            description = "The user is not authenticated",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @GetMapping(ME)
    public ResponseEntity<ResponseUserDto> returnCurrentUser(
            Principal principal, Locale locale
    ) {
        return ResponseEntity.ok(
                userService
                        .returnUserByEmail(principal.getName(), locale)
        );
    }

    @Operation(
            summary = "Update user information",
            description = "Allows an authenticated user to update own personal information",
            parameters = {
                    @Parameter(
                            name = "userUpdateDto",
                            description = "The new personal data of the user",
                            required = true,
                            schema = @Schema(implementation = UserUpdateDto.class)
                    ),
                    @Parameter(
                            name = "Accept-Language",
                            description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                            schema = @Schema(type = "string", example = "uk")
                    )
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "User has been successfully updated",
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
    @ApiResponse(
            responseCode = "401",
            description = "The user is not authenticated",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @PatchMapping(ME)
    public ResponseEntity<ResponseUserDto> updateYourself(
            @RequestBody @Valid UserUpdateDto userUpdateDto,
            Principal principal, Locale locale
    ) {
        return ResponseEntity.ok(
                userService
                        .updateUserByEmail(
                                principal.getName(), userUpdateDto, locale
                        )
        );
    }

    @Operation(
            summary = "Change user password",
            description = "Allows authenticated users to change their password",
            parameters = {
                    @Parameter(
                            name = "passwordDto",
                            description = "New password details",
                            required = true,
                            schema = @Schema(implementation = PasswordDto.class)
                    ),
                    @Parameter(
                            name = "Accept-Language",
                            description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                            schema = @Schema(type = "string", example = "uk")
                    )
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Password has been successfully changed",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid password format",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "401",
            description = "The user is not authenticated",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @PatchMapping(CHANGE_PASSWORD)
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestBody @Valid PasswordDto passwordDto,
            Principal principal, Locale locale
    ) {
        return ResponseEntity.ok().body(
                userService.updatePassword(
                        principal.getName(), passwordDto, locale
                )
        );
    }

    @Operation(
            summary = "Delete user account",
            description = "Allows an authenticated user to delete their own account",
            parameters = {
                    @Parameter(
                            name = "Accept-Language",
                            description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                            schema = @Schema(type = "string", example = "en")
                    )
            }
    )
    @ApiResponse(
            responseCode = "204",
            description = "User account successfully deleted"
    )
    @ApiResponse(
            responseCode = "401",
            description = "The user is not authenticated",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @DeleteMapping(ME)
    public ResponseEntity<Void> deleteYourself(
            Principal principal, Locale locale
    ) {
        userService.deleteUserByEmail(principal.getName(), locale);
        return ResponseEntity.noContent().build();
    }
}
