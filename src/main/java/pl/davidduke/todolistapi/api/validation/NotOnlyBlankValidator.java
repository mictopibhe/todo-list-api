package pl.davidduke.todolistapi.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotOnlyBlankValidator implements ConstraintValidator<NotOnlyBlank, String> {
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return string == null || !string.isBlank();
    }
}
