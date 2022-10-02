package ru.practicum.ewmmain.exception.compilation;

/**
 * Подборка событий не найдена.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.compilation.Compilation
 */
public class CompilationNotFoundException extends RuntimeException {
    public CompilationNotFoundException(String message) {
        super(message);
    }
}
