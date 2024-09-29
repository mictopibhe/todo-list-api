package pl.davidduke.todolistapi.api.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import pl.davidduke.todolistapi.api.validation.PasswordMatches;

@Data
@Builder
public class UserUpdateDto {
    @Size(min = 2, max = 70, message = "{error.name.length}")
    private String firstName;
    @Size(min = 2, max = 70, message = "{error.lastname.length}")
    private String lastName;
    @Size(min = 2, max = 70, message = "{error.email.length}")
    private String email;
}
