package ru.practicum.ewmmain.exception.event;

/**
 * Событие не найдено.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.Event
 */
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
