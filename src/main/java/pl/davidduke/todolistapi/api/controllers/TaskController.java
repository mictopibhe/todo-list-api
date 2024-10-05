package pl.davidduke.todolistapi.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.davidduke.todolistapi.api.dto.tasks.TaskCreateDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskUpdateDto;
import pl.davidduke.todolistapi.api.dto.tasks.TasksPageDto;
import pl.davidduke.todolistapi.api.services.TaskService;

import java.security.Principal;
import java.util.Locale;

@RestController
@RequestMapping("${api.endpoint.base-url}")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
public class TaskController {

    private static final String FETCH_OR_CREATE = "/tasks";
    private static final String GET_OR_UPDATE_OR_DELETE = "/tasks/{id}";

    private final TaskService taskService;

    @GetMapping(FETCH_OR_CREATE)
    public ResponseEntity<TasksPageDto<TaskDto>> fetchAllTasks(
            Pageable pageable, Principal principal
    ) {
        return ResponseEntity.ok(
                taskService.fetchTasks(
                        principal.getName(), pageable
                )
        );
    }

    @GetMapping(GET_OR_UPDATE_OR_DELETE)
    public ResponseEntity<TaskDto> fetchTaskByOwnerAndId(
            @PathVariable Long id, Principal principal,
            Locale locale
    ) {
        return ResponseEntity.ok(
                taskService.fetchTaskByOwnerAndId(
                        principal.getName(), id, locale
                )
        );
    }

    @PostMapping(FETCH_OR_CREATE)
    public ResponseEntity<TaskDto> createTask(
            @RequestBody @Valid TaskCreateDto taskDto,
            Principal principal, Locale locale
    ) {
        return ResponseEntity.ok(
                taskService.createTask(
                        taskDto, principal.getName(), locale
                )
        );
    }

//    @PatchMapping(GET_OR_UPDATE_OR_DELETE)
//    public ResponseEntity<TaskDto> updateTaskByOwnerAndId(
//            @RequestBody @Valid TaskUpdateDto taskUpdateDto,
//            @PathVariable long id, Principal principal,
//            Locale locale) {
//        return ResponseEntity.ok(
//                taskService.updateTaskByOwnerAndId(
//                        principal.getName(), id, taskUpdateDto, locale
//                )
//        );
//    }

//    @DeleteMapping(GET_OR_UPDATE_OR_DELETE)
//    public ResponseEntity<Void> deleteTaskByOwnerAndId(
//            @PathVariable Long id, Principal principal,
//            Locale locale
//    ) {
//        taskService.deleteTaskByOwnerAndId(
//                principal.getName(), id, locale
//        );
//        return ResponseEntity.noContent().build();
//    }
}
