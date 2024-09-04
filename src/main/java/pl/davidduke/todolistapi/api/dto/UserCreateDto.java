package pl.davidduke.todolistapi.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateDto {
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 100, message = "The first name must contain between 2 and 100 characters")
    @Pattern(regexp = "^\\p{L}+$", message = "The first name must contain only letters")
    String firstName;
    @NotBlank(message = "Last name must not be blank")
    @Size(min = 2, max = 100, message = "The last name must contain between 2 and 100 characters")
    @Pattern(regexp = "^\\p{L}+$", message = "The last name must contain only letters")
    String lastName;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    String email;
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    String password;
}
