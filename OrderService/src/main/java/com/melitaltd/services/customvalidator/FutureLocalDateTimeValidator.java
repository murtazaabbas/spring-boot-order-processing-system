package com.melitaltd.services.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class FutureLocalDateTimeValidator implements ConstraintValidator<FutureLocalDateTime, LocalDateTime> {
    @Override
    public void initialize(FutureLocalDateTime constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (localDateTime == null) {
                return false; // Null values are considered valid
            }
            LocalDateTime currentDateTime = LocalDateTime.now();
            return localDateTime.isAfter(currentDateTime);
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
}
