package pl.davidduke.todolistapi.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pl.davidduke.todolistapi.storage.entities.TaskEntity;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseUserDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    String password;
    List<TaskEntity> tasks;
}
