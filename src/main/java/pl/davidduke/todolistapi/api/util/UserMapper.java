package pl.davidduke.todolistapi.api.util;

import org.mapstruct.Mapper;
import pl.davidduke.todolistapi.api.dto.UserCreateDto;
import pl.davidduke.todolistapi.api.dto.ResponseUserDto;
import pl.davidduke.todolistapi.storage.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResponseUserDto userEntityToResponseUserDto(UserEntity user);
    UserEntity userCreateDtoToUserEntity(UserCreateDto userCreateDto);
}
