package ru.practicum.ewmmain.exception.setlocation;

/**
 * Локация не найдена.
 * Extends RuntimeException.
 *
 * @author Evgeny S
 */
public class SetLocationNotFoundException extends RuntimeException{
    public SetLocationNotFoundException(String message) {
        super(message);
    }
}
