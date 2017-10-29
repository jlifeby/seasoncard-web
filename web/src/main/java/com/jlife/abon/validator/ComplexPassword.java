package com.jlife.abon.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Dzmitry Misiuk
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ComplexPasswordValidator.class)

public @interface ComplexPassword {
    String message() default "Password is too easy.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}