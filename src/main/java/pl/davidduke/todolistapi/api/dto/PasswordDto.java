package pl.davidduke.todolistapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordDto {
    @NotBlank(message = "{error.password.blank}")
    @Size(min = 8, max = 70, message = "{error.password.length}")
    private String oldPassword;
    @NotBlank(message = "{error.password.blank}")
    @Size(min = 8, max = 70, message = "{error.password.length}")
    private String newPassword;

}
