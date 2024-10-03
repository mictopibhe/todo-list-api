package pl.davidduke.todolistapi.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.davidduke.todolistapi.api.dto.users.UserCreateDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserCreateDto> {
    @Override
    public boolean isValid(
            UserCreateDto userCreateDto,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return userCreateDto.getPassword().equals(userCreateDto.getConfirmPassword());
    }
}
