package ru.practicum.ewmmain.utils.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EventDateValidator.class)
@Documented
public @interface TwoHoursAfterNow {

    String message() default "Event date and time has to be not earlier than 2 hours after now";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

