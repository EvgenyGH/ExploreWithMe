package ru.practicum.ewmmain.exception;

public class OperationConditionViolationException extends RuntimeException{
    public OperationConditionViolationException(String message) {
        super(message);
    }
}
