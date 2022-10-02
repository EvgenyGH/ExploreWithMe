package ru.practicum.ewmmain.exception.user;

/**
 * Пользователь не найден.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.user.User
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
