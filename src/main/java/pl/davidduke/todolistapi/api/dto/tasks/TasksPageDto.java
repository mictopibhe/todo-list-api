package pl.davidduke.todolistapi.api.dto.tasks;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TasksPageDto<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
}
