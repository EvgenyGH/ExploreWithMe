package ru.practicum.ewmmain.event.validator;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDateTime;

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

class EventDateValidator implements ConstraintValidator<TwoHoursAfterNow, LocalDateTime> {

    @Override
    //дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        return eventDate.isAfter(LocalDateTime.now().plusHours(2));
    }
}