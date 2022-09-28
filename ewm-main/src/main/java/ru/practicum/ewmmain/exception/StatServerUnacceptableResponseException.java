package ru.practicum.ewmmain.exception;

public class StatServerUnacceptableResponseException extends RuntimeException {
    public StatServerUnacceptableResponseException(String message) {
        super(message);
    }
}
