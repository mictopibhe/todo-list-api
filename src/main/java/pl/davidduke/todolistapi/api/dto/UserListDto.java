package pl.davidduke.todolistapi.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserListDto<T> {
    List<T> content;
    long totalElements;
    int totalPages;
    int pageNumber;
    int pageSize;
}
