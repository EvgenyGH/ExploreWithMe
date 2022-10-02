package ru.practicum.ewmmain.exception.participationrequest;

/**
 * Заявка на участие не найдена.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.participationrequest.ParticipationRequest
 */
public class ParticipationRequestNotFoundException extends RuntimeException {
    public ParticipationRequestNotFoundException(String message) {
        super(message);
    }
}
