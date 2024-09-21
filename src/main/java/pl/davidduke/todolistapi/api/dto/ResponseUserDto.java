package pl.davidduke.todolistapi.api.dto;

import lombok.Builder;
import lombok.Data;
import pl.davidduke.todolistapi.storage.entities.TaskEntity;

import java.util.List;

@Data
@Builder
public class ResponseUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<TaskEntity> tasks;
}
