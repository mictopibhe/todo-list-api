package pl.davidduke.todolistapi.api.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import pl.davidduke.todolistapi.api.validation.NotOnlyBlank;

@Data
@Builder
public class UserUpdateDto {
    @Size(min = 2, max = 70, message = "{error.name.length}")
    @NotOnlyBlank(message = "{error.name.blank}")
    private String firstName;
    @Size(min = 2, max = 70, message = "{error.lastname.length}")
    @NotOnlyBlank(message = "{error.lastname.blank}")
    private String lastName;
    @Size(min = 2, max = 70, message = "{error.email.length}")
    @NotOnlyBlank(message = "{error.email.blank}")
    private String email;
}
