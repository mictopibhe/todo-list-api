package pl.davidduke.todolistapi.api.dto.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreateDto {
    @NotBlank(message = "")
    @Size(max = 255, message = "")
    private String title;
    private String description;
}
