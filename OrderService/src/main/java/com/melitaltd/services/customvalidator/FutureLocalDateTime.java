package com.melitaltd.services.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureLocalDateTimeValidator.class)
@Documented
public @interface FutureLocalDateTime {
    String message() default "Date should be in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
