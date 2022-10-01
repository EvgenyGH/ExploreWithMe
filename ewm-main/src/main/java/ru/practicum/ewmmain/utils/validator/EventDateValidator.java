package ru.practicum.ewmmain.utils.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class EventDateValidator implements ConstraintValidator<TwoHoursAfterNow, LocalDateTime> {

    @Override
    //дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        return eventDate.isAfter(LocalDateTime.now().plusHours(2));
    }
}
