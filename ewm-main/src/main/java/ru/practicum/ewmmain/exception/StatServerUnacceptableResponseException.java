package ru.practicum.ewmmain.exception;

/**
 * Ошибка сервера статистики.
 * @author Evgeny S
 */
public class StatServerUnacceptableResponseException extends RuntimeException {
    public StatServerUnacceptableResponseException(String message) {
        super(message);
    }
}
