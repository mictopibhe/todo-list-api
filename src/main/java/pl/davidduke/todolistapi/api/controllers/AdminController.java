package pl.davidduke.todolistapi.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.davidduke.todolistapi.api.dto.errors.ApiErrorDto;
import pl.davidduke.todolistapi.api.dto.users.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.users.UserUpdateDto;
import pl.davidduke.todolistapi.api.dto.users.UsersPageDto;
import pl.davidduke.todolistapi.api.services.UserService;

import java.util.Locale;

@RestController
@RequestMapping("${api.endpoint.base-url}/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@SecurityRequirement(name = "httpBasic")
public class AdminController {

    private static final String ID = "/{id}";

    private final UserService userService;

    @Operation(
            summary = "Fetch a paginated list of users (Admin only)",
            description = "Allows administrators to fetch a paginated list of all users",
            tags = {"Admin Operations"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "Users successfully fetched",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsersPageDto.class)
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
    @ApiResponse(
            responseCode = "403",
            description = "Full authentication is required to access this resource",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<UsersPageDto<ResponseUserDto>> fetchUsers(
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(
                userService
                        .fetchUsers(pageable)
        );
    }

    @Operation(
            summary = "Fetch information about the user by specified ID (Admin only)",
            description = "Allows administrators to fetch information about the user by its ID",
            tags = {"Admin Operations"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "The user's data has been successfully received",
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
    @ApiResponse(
            responseCode = "403",
            description = "The user does not have the necessary permissions (Admin role required).",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @GetMapping(ID)
    public ResponseEntity<ResponseUserDto> fetchUserById(
            @Parameter(description = "User ID", example = "1") @PathVariable long id,
            @Parameter(description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
            name = "Accept-Language", in = ParameterIn.HEADER) Locale locale
    ) {
        return ResponseEntity.ok(
                userService
                        .returnUserById(id, locale)
        );
    }

    @Operation(
            summary = "Update user's information by specified ID (Admin only)",
            description = "Allows administrators to update user's information by its ID",
            tags = {"Admin Operations"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "The user's data has been successfully updated",
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
    @ApiResponse(
            responseCode = "403",
            description = "The user does not have the necessary permissions (Admin role required).",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @PatchMapping(ID)
    public ResponseEntity<ResponseUserDto> updateUserById(
            @Parameter(description = "User ID", example = "1") @PathVariable long id,
            @RequestBody @Valid UserUpdateDto userUpdateDto,
            @Parameter(description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                    name = "Accept-Language", in = ParameterIn.HEADER) Locale locale
    ) {
        return ResponseEntity.ok(
                userService
                        .updateUserById(id, userUpdateDto,
                                locale
                        )
        );
    }

    @Operation(
            summary = "Delete user profile by specified ID (Admin only)",
            description = "Allows administrators to delete user's profile by its ID",
            tags = {"Admin Operations"}
    )
    @ApiResponse(
            responseCode = "204",
            description = "The user's profile has been successfully deleted"
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
    @ApiResponse(
            responseCode = "403",
            description = "The user does not have the necessary permissions (Admin role required).",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @DeleteMapping(ID)
    public ResponseEntity<Void> deleteUserById(
            @Parameter(description = "User ID", example = "1") @PathVariable long id,
            @Parameter(description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                    name = "Accept-Language", in = ParameterIn.HEADER) Locale locale
    ) {
        userService.deleteUserById(id, locale);
        return ResponseEntity.noContent().build();
    }
}
