package pl.davidduke.todolistapi.api.dto.tasks;

import lombok.Builder;
import lombok.Data;
import pl.davidduke.todolistapi.storage.enums.TaskStatus;

@Data
@Builder
public class TaskUpdateDto {
    private String title;
    private String description;

    private TaskStatus status;
}
