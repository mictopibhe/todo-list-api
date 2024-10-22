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
import pl.davidduke.todolistapi.api.dto.tasks.TaskCreateDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskUpdateDto;
import pl.davidduke.todolistapi.api.dto.tasks.TasksPageDto;
import pl.davidduke.todolistapi.api.services.TaskService;

import java.security.Principal;
import java.util.Locale;

@RestController
@RequestMapping("${api.endpoint.base-url}/tasks")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
@SecurityRequirement(name = "httpBasic")
public class TaskController {

    private static final String ID = "/{id}";

    private final TaskService taskService;

    @Operation(
            summary = "Fetch a paginated list of tasks for the authenticated user",
            description = "Returns a paginated list of tasks associated with the authenticated user",
            tags = {"Task Operations"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "Paginated list of tasks successfully fetched",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TasksPageDto.class)
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
            description = "The user does not have permission to access this resource",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<TasksPageDto<TaskDto>> fetchAllTasks(
            @ParameterObject Pageable pageable, Principal principal
    ) {
        return ResponseEntity.ok(
                taskService.fetchTasks(
                        principal.getName(), pageable
                )
        );
    }

    @Operation(
            summary = "Fetch a task with specified id for the authenticated user",
            description = "Returns a specific task associated with the authenticated user",
            tags = {"Task Operations"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "Task successfully fetched",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)
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
            description = "The user does not have permission to access this resource",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @GetMapping(ID)
    public ResponseEntity<TaskDto> fetchTaskByOwnerAndId(
            @Parameter(description = "Task ID", example = "1") @PathVariable Long id, Principal principal,
            @Parameter(description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                    name = "Accept-Language", in = ParameterIn.HEADER) Locale locale
    ) {
        return ResponseEntity.ok(
                taskService.fetchTaskByOwnerAndId(
                        principal.getName(), id, locale
                )
        );
    }

    @Operation(
            summary = "Create a new task for the authenticated user",
            description = "Allows the authenticated user to create a new task.",
            tags = {"Task Operations"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Task creation data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskCreateDto.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "201",
            description = "Task successfully created",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid task data provided",
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
            description = "The user does not have permission to create tasks",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @PostMapping(ID)
    public ResponseEntity<TaskDto> createTask(
            @RequestBody @Valid TaskCreateDto taskDto,
            Principal principal,
            @Parameter(description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                    name = "Accept-Language", in = ParameterIn.HEADER) Locale locale
    ) {
        return ResponseEntity.ok(
                taskService.createTask(
                        taskDto, principal.getName(), locale
                )
        );
    }

    @Operation(
            summary = "Update a task by ID for the authenticated user",
            description = "Allows the authenticated user to update a specific task by its ID.",
            tags = {"Task Operations"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Task update data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskUpdateDto.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Task successfully updated",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data provided for task update",
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
            description = "The user does not have permission to update this task",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @PatchMapping(ID)
    public ResponseEntity<TaskDto> updateTaskByOwnerAndId(
            @RequestBody @Valid TaskUpdateDto taskUpdateDto,
            @Parameter(description = "Task ID", example = "1")@PathVariable long id,
            Principal principal,
            @Parameter(description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                    name = "Accept-Language", in = ParameterIn.HEADER) Locale locale
    ) {
        return ResponseEntity.ok(
                taskService.updateTaskByOwnerAndId(
                        principal.getName(), id, taskUpdateDto, locale
                )
        );
    }

    @Operation(
            summary = "Delete a task by ID for the authenticated user",
            description = "Allows the authenticated user to delete a specific task by its ID.",
            tags = {"Task Operations"}
    )
    @ApiResponse(
            responseCode = "204",
            description = "Task successfully deleted"
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
            description = "The user does not have permission to delete this task",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorDto.class)
                    )
            }
    )
    @DeleteMapping(ID)
    public ResponseEntity<Void> deleteTaskByOwnerAndId(
            @Parameter(description = "Task ID", example = "1") @PathVariable Long id, Principal principal,
            @Parameter(description = "Locale for the response content (supported: 'uk', 'en', 'pl')",
                    name = "Accept-Language", in = ParameterIn.HEADER) Locale locale
    ) {
        taskService.deleteTaskByOwnerAndId(
                principal.getName(), id, locale
        );
        return ResponseEntity.noContent().build();
    }
}
