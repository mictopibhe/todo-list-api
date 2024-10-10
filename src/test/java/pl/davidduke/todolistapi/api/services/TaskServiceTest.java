package pl.davidduke.todolistapi.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.davidduke.todolistapi.api.util.TaskMapper;
import pl.davidduke.todolistapi.storage.repositories.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;

    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void fetchTasks() {
    }

    @Test
    void createTask() {
    }

    @Test
    void fetchTaskByOwnerAndId() {
    }

    @Test
    void updateTaskByOwnerAndId() {
    }

    @Test
    void deleteTaskByOwnerAndId() {
    }
}