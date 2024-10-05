package pl.davidduke.todolistapi.api.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.davidduke.todolistapi.api.dto.tasks.TaskCreateDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskUpdateDto;
import pl.davidduke.todolistapi.storage.entities.TaskEntity;
import pl.davidduke.todolistapi.storage.entities.UserEntity;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaskMapper {
    @Mapping(source = "owner", target = "ownerId")
    TaskDto entityToTaskDto(TaskEntity taskEntity);

    TaskEntity taskCreateDtoToEntity(TaskCreateDto taskDto);
    void updatePartial(TaskUpdateDto taskUpdateDto, @MappingTarget TaskEntity taskEntity);

    default long mapOwnerToId(UserEntity user) {
        return user.getId();
    }
}
