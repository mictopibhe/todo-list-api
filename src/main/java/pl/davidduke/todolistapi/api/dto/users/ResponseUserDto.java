package pl.davidduke.todolistapi.api.dto.users;

import lombok.Builder;
import lombok.Data;
import pl.davidduke.todolistapi.api.dto.tasks.TaskDto;
import pl.davidduke.todolistapi.storage.entities.TaskEntity;
import pl.davidduke.todolistapi.storage.enums.Role;

import java.util.List;

@Data
@Builder
public class ResponseUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private List<TaskDto> tasks;
}
