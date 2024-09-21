package pl.davidduke.todolistapi.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.davidduke.todolistapi.api.dto.PostUserDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PostUserDto> {
    @Override
    public boolean isValid(
            PostUserDto postUserDto,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return postUserDto.getPassword().equals(postUserDto.getMatchingPassword());
    }
}
