package pl.davidduke.todolistapi.api.util;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.davidduke.todolistapi.storage.entities.UserEntity;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    ResponseUserDto userEntityToResponseUserDto(UserEntity user);

    UserEntity userCreateDtoToUserEntity(UserCreateDto userCreateDto);

    void patchUserEntity(
            UserPatchDto userPatchDto,
            @MappingTarget UserEntity user
    );

    void putUserEntity(
            UserCreateDto userCreateDto,
            @MappingTarget UserEntity user
    );
}
