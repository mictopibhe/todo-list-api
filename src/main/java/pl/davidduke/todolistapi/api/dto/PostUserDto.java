package pl.davidduke.todolistapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUserDto {
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String email;
    @NotBlank
    String password;
    @NotBlank
    String matchingPassword;
}
