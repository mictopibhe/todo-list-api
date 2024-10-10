package pl.davidduke.todolistapi.api.util;

import org.mapstruct.*;
import pl.davidduke.todolistapi.api.dto.users.ResponseUserDto;
import pl.davidduke.todolistapi.api.dto.users.UserCreateDto;
import pl.davidduke.todolistapi.api.dto.users.UserUpdateDto;
import pl.davidduke.todolistapi.storage.entities.UserEntity;
import pl.davidduke.todolistapi.storage.enums.Role;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TaskMapper.class}
)
public interface UserMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "tasks", source = "tasks")
    @Mapping(target = "email", source = "email", qualifiedByName = "toLowerCase")
//    @Mapping(target = "email", source = "email")
    ResponseUserDto entityToResponseUserDto(UserEntity user);

    //    @Mapping(target = "email",  expression = "java(toLowerCase(userDto.getEmail()))")
    @Mapping(target = "email", source = "email", qualifiedByName = "toLowerCase")
    @Mapping(target = "role", expression = "java(getDefaultRole())")
    UserEntity toEntity(UserCreateDto userDto);

    //    @Mapping(target = "email", expression = "java(toLowerCase(userUpdateDto.getEmail()))")
    @Mapping(target = "email", source = "email", qualifiedByName = "toLowerCase")
    void updatePartial(UserUpdateDto userUpdateDto, @MappingTarget UserEntity user);

    @Named("toLowerCase")
    default String toLowerCase(String email) {
        return email != null ? email.toLowerCase() : null;
    }
    default Role getDefaultRole() {
        return Role.ROLE_USER;
    }
}
