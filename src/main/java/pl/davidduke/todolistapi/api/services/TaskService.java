package pl.davidduke.todolistapi.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.davidduke.todolistapi.api.dto.tasks.TaskCreateDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskUpdateDto;
import pl.davidduke.todolistapi.api.dto.tasks.TasksPageDto;
import pl.davidduke.todolistapi.api.exceptions.InvalidTaskStatusException;
import pl.davidduke.todolistapi.api.exceptions.TaskByUserNotFoundException;
import pl.davidduke.todolistapi.api.util.TaskMapper;
import pl.davidduke.todolistapi.storage.entities.TaskEntity;
import pl.davidduke.todolistapi.storage.entities.UserEntity;
import pl.davidduke.todolistapi.storage.enums.TaskStatus;
import pl.davidduke.todolistapi.storage.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;
    private final MessageSource messageSource;

    public TasksPageDto<TaskDto> fetchTasks(
            String username, Pageable pageable
    ) {
        Page<TaskEntity> tasks = taskRepository
                .findAllByOwnerEmail(username, pageable);

        List<TaskDto> tasksList = tasks.map(
                taskMapper::entityToTaskDto
        ).toList();

        return TasksPageDto
                .<TaskDto>builder()
                .content(tasksList)
                .totalElements(tasks.getTotalElements())
                .totalPages(tasks.getTotalPages())
                .pageNumber(tasks.getNumber())
                .build();
    }

    @Transactional
    public TaskDto createTask(
            TaskCreateDto taskDto, String name, Locale locale
    ) {
        UserEntity user = userService.findUserByEmail(name, locale);

        TaskEntity task = taskMapper.taskCreateDtoToEntity(taskDto);
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(TaskStatus.PLANNED);
        task.setOwner(user);
        return taskMapper.entityToTaskDto(taskRepository.save(task));
    }

    public TaskDto fetchTaskByOwnerAndId(
            String name, Long id, Locale locale
    ) {
        return taskMapper
                .entityToTaskDto(fetchTaskByOwnerAndIdOrThrowException(name, id, locale));
    }

    @Transactional
    public TaskDto updateTaskByOwnerAndId(
            String name, long id, TaskUpdateDto taskUpdateDto, Locale locale) {
        TaskEntity taskToBeUpdated = fetchTaskByOwnerAndIdOrThrowException(
                name, id, locale
        );
        TaskStatus newStatus = taskUpdateDto.getStatus();
        if (isPossibleChangeStatus(newStatus, taskToBeUpdated.getStatus())) {
            if (newStatus.equals(TaskStatus.DONE) &&
                    !taskToBeUpdated.getStatus().equals(TaskStatus.DONE)) {
                taskToBeUpdated.setFinishedAt(LocalDateTime.now());
            }
            taskToBeUpdated.setStatus(newStatus);
        } else {
            throw new InvalidTaskStatusException(
                    messageSource.getMessage(
                            "invalid.task.status",
                            new Object[]{taskToBeUpdated.getStatus(), newStatus},
                            locale
                    )
            );
        }
        taskMapper.updatePartial(taskUpdateDto, taskToBeUpdated);
        return taskMapper.entityToTaskDto(taskToBeUpdated);
    }

    private boolean isPossibleChangeStatus(TaskStatus newStatus, TaskStatus status) {
        return newStatus == null || status.canTransitionTo(newStatus);
    }

    @Transactional
    public void deleteTaskByOwnerAndId(
            String name, Long id, Locale locale
    ) {
        taskRepository.delete(fetchTaskByOwnerAndIdOrThrowException(name, id, locale));
    }

    private TaskEntity fetchTaskByOwnerAndIdOrThrowException(
            String ownerName, Long id, Locale locale
    ) {
        return taskRepository.findByOwner_EmailAndId(ownerName, id)
                .orElseThrow(() -> new TaskByUserNotFoundException(
                        messageSource.getMessage(
                                "error.taskByUser.notFound",
                                new Object[]{ownerName, id},
                                locale
                        )
                ));
    }
}
