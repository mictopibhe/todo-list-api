package pl.davidduke.todolistapi.api.util;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.davidduke.todolistapi.api.dto.tasks.TaskCreateDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskDto;
import pl.davidduke.todolistapi.api.dto.tasks.TaskUpdateDto;
import pl.davidduke.todolistapi.storage.entities.TaskEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-06T14:58:28+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto entityToTaskDto(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskDto.TaskDtoBuilder taskDto = TaskDto.builder();

        taskDto.ownerId( mapOwnerToId( taskEntity.getOwner() ) );
        taskDto.id( taskEntity.getId() );
        taskDto.title( taskEntity.getTitle() );
        taskDto.description( taskEntity.getDescription() );
        taskDto.createdAt( taskEntity.getCreatedAt() );
        taskDto.finishedAt( taskEntity.getFinishedAt() );
        taskDto.status( taskEntity.getStatus() );

        return taskDto.build();
    }

    @Override
    public TaskEntity taskCreateDtoToEntity(TaskCreateDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setTitle( taskDto.getTitle() );
        taskEntity.setDescription( taskDto.getDescription() );

        return taskEntity;
    }

    @Override
    public void updatePartial(TaskUpdateDto taskUpdateDto, TaskEntity taskEntity) {
        if ( taskUpdateDto == null ) {
            return;
        }

        if ( taskUpdateDto.getTitle() != null ) {
            taskEntity.setTitle( taskUpdateDto.getTitle() );
        }
        if ( taskUpdateDto.getDescription() != null ) {
            taskEntity.setDescription( taskUpdateDto.getDescription() );
        }
        if ( taskUpdateDto.getStatus() != null ) {
            taskEntity.setStatus( taskUpdateDto.getStatus() );
        }
    }
}
