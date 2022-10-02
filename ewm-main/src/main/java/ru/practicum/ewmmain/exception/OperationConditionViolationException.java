package ru.practicum.ewmmain.exception;

/**
 * Условия для выполнения операции не выполнены.
 * @author Evgeny S
 */
public class OperationConditionViolationException extends RuntimeException {
    public OperationConditionViolationException(String message) {
        super(message);
    }
}
