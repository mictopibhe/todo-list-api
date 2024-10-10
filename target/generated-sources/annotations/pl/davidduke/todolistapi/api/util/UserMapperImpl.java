package pl.davidduke.todolistapi.api.util;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.davidduke.todolistapi.api.dto.tasks.TaskDto;
import pl.davidduke.todolistapi.api.dto.users.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.users.UserCreateDto;
import pl.davidduke.todolistapi.api.dto.users.UserUpdateDto;
import pl.davidduke.todolistapi.storage.entities.TaskEntity;
import pl.davidduke.todolistapi.storage.entities.UserEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-08T21:42:00+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public ResponseUserDto entityToResponseUserDto(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        ResponseUserDto.ResponseUserDtoBuilder responseUserDto = ResponseUserDto.builder();

        responseUserDto.id( user.getId() );
        responseUserDto.firstName( user.getFirstName() );
        responseUserDto.lastName( user.getLastName() );
        responseUserDto.role( user.getRole() );
        responseUserDto.tasks( taskEntityListToTaskDtoList( user.getTasks() ) );
        responseUserDto.email( toLowerCase( user.getEmail() ) );

        return responseUserDto.build();
    }

    @Override
    public UserEntity toEntity(UserCreateDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail( toLowerCase( userDto.getEmail() ) );
        userEntity.setFirstName( userDto.getFirstName() );
        userEntity.setLastName( userDto.getLastName() );
        userEntity.setPassword( userDto.getPassword() );

        return userEntity;
    }

    @Override
    public void updatePartial(UserUpdateDto userUpdateDto, UserEntity user) {
        if ( userUpdateDto == null ) {
            return;
        }

        if ( userUpdateDto.getEmail() != null ) {
            user.setEmail( toLowerCase( userUpdateDto.getEmail() ) );
        }
        if ( userUpdateDto.getFirstName() != null ) {
            user.setFirstName( userUpdateDto.getFirstName() );
        }
        if ( userUpdateDto.getLastName() != null ) {
            user.setLastName( userUpdateDto.getLastName() );
        }
    }

    protected List<TaskDto> taskEntityListToTaskDtoList(List<TaskEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<TaskDto> list1 = new ArrayList<TaskDto>( list.size() );
        for ( TaskEntity taskEntity : list ) {
            list1.add( taskMapper.entityToTaskDto( taskEntity ) );
        }

        return list1;
    }
}
