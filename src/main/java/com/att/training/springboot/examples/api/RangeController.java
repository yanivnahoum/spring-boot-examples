package com.att.training.springboot.examples.api;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

record Range(int low, int high) {}

@RestController
@RequestMapping("range")
@Validated
public class RangeController {
    @GetMapping
    public int getRange(@ValidRange @RequestBody Range range) {
        return range.high() - range.low();
    }
}

@Constraint(validatedBy = RangeValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@interface ValidRange {
    String message() default "Low must be less than high";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class RangeValidator implements ConstraintValidator<ValidRange, Range> {
    @Override
    public boolean isValid(Range range, ConstraintValidatorContext context) {
        return range == null || range.low() < range.high();
    }
}

