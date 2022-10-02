package ru.practicum.ewmmain.exception.event;

/**
 * Категория не найдена.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.category.Category
 */
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
