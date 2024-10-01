package pl.davidduke.todolistapi.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {NotOnlyBlankValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotOnlyBlank {
    String message() default "The field not be blank";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
