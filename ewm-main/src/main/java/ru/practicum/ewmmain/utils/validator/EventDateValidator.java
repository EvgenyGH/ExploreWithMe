package ru.practicum.ewmmain.utils.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * Валидация даты и времени на которые намечено событие.
 * Дата и время не могут быть раньше, чем через два часа от текущего момента.
 * @author Evgeny S
 * @see TwoHoursAfterNow
 */
public class EventDateValidator implements ConstraintValidator<TwoHoursAfterNow, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        return eventDate.isAfter(LocalDateTime.now().plusHours(2));
    }
}
