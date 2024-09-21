package pl.davidduke.todolistapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pl.davidduke.todolistapi.api.validation.PasswordMatches;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@PasswordMatches(message = "{error.passwords.dontmatch}")
public class PostUserDto {
    @NotBlank(message = "{error.name.blank}")
    @Size(min = 2, max = 70, message = "{error.name.length}")
    String firstName;
    @NotBlank(message = "{error.lastname.blank}")
    @Size(min = 2, max = 70, message = "{error.lastname.length}")
    String lastName;
    @NotBlank(message = "{error.email.blank}")
    @Size(min = 2, max = 70, message = "{error.email.length}")
    String email;
    @NotBlank(message = "{error.password.blank}")
    @Size(min = 8, max = 70, message = "{error.password.length}")
    String password;

    String matchingPassword;
}
