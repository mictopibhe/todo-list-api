package pl.davidduke.todolistapi.api.util;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.davidduke.todolistapi.api.dto.PostUserDto;
import pl.davidduke.todolistapi.api.dto.ResponseUserDto;
import pl.davidduke.todolistapi.storage.entities.UserEntity;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    ResponseUserDto userEntityToResponseUserDto(UserEntity user);
    UserEntity postUserDtoToUserEntity(PostUserDto userDto);
}
