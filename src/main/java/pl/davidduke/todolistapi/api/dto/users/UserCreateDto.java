package pl.davidduke.todolistapi.api.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import pl.davidduke.todolistapi.api.validation.PasswordMatches;

@Data
@Builder
@PasswordMatches(message = "{error.passwords.dontmatch}")
public class UserCreateDto {
    @NotBlank(message = "{error.name.blank}")
    @Size(min = 2, max = 70, message = "{error.name.length}")
    private String firstName;
    @NotBlank(message = "{error.lastname.blank}")
    @Size(min = 2, max = 70, message = "{error.lastname.length}")
    private String lastName;
    @NotBlank(message = "{error.email.blank}")
    @Size(min = 2, max = 70, message = "{error.email.length}")
    @Email
    private String email;
    @NotBlank(message = "{error.password.blank}")
    @Size(min = 8, max = 70, message = "{error.password.length}")
    private String password;

    private String confirmPassword;
}
