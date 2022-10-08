package ru.practicum.ewmmain.exception.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.ewmmain.exception.compilation.CompilationNotFoundException;
import ru.practicum.ewmmain.model.errormodel.ErrorResponse;
import ru.practicum.ewmmain.exception.event.CategoryNotFoundException;
import ru.practicum.ewmmain.exception.event.EventNotFoundException;
import ru.practicum.ewmmain.exception.OperationConditionViolationException;
import ru.practicum.ewmmain.exception.participationrequest.ParticipationRequestNotFoundException;
import ru.practicum.ewmmain.exception.user.UserNotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

/**
 * Обработчик ошибок модуля ewm-main.
 * @author Evgeny S
 */
@RestControllerAdvice({"ru.practicum.ewmmain"})
@Slf4j
public class EwmExceptionHandler {
    private static final String INTEGRITY_VIOLATION_MSG = "Integrity constraint has been violated";
    private static final String INTERNAL_ERROR_MSG = "Error occurred";
    private static final String BAD_REQUEST_MSG = "Bad request";
    private static final String NOT_FOUND_MSG = "The required object was not found";
    private static final String FORBIDDEN_MSG = "Operation conditions violated";

    /**
     * Обработчик ошибочно составленных запросов.
     * 400 BAD_REQUEST.
     * @param exception одно из исключений:
     *                  {@link ConstraintViolationException},
     *                  {@link MethodArgumentTypeMismatchException},
     *                  {@link MethodArgumentNotValidException}.
     * @return возвращает сообщение об ошибке {@link ErrorResponse}.
     */
    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse badRequestHandler(Exception exception) {
        ErrorResponse response = new ErrorResponse(exception.getStackTrace(),
                exception.getMessage(),
                BAD_REQUEST_MSG,
                HttpStatus.BAD_REQUEST.name(), LocalDateTime.now());

        log.warn("{} Exception:{} Message:{}",
                response.getTimestamp(), exception.getClass().getSimpleName(),
                exception.getMessage());
        return response;
    }

    /**
     * Обработчик исключений о невыполнении условий для совершения операции.
     * 403 FORBIDDEN.
     * @param exception исключение {@link OperationConditionViolationException}.
     * @return возвращает сообщение об ошибке {@link ErrorResponse}.
     */
    //403 FORBIDDEN Не выполнены условия для совершения операции
    @ExceptionHandler({OperationConditionViolationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse forbiddenHandler(OperationConditionViolationException exception) {
        ErrorResponse response = new ErrorResponse(exception.getStackTrace(),
                exception.getMessage(),
                FORBIDDEN_MSG,
                HttpStatus.FORBIDDEN.name(), LocalDateTime.now());

        log.warn("{} Exception:{} Message:{}",
                response.getTimestamp(), exception.getClass().getSimpleName(),
                exception.getMessage());
        return response;
    }

    /**
     * Обработчик исключений ресурс не найден.
     * 404 NOT_FOUND.
     * @param exception одно из исключений:  {@link EmptyResultDataAccessException},
     *                  {@link CategoryNotFoundException},
     *                  {@link UserNotFoundException},
     *                  {@link ParticipationRequestNotFoundException},
     *                  {@link CompilationNotFoundException},
     *                  {@link EventNotFoundException}.
     * @return возвращает сообщение об ошибке {@link ErrorResponse}.
     */
    @ExceptionHandler({EmptyResultDataAccessException.class, CategoryNotFoundException.class,
            UserNotFoundException.class, ParticipationRequestNotFoundException.class,
            CompilationNotFoundException.class, EventNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse notFoundHandler(Exception exception) {
        ErrorResponse response = new ErrorResponse(null,
                exception.getMessage(),
                NOT_FOUND_MSG,
                HttpStatus.NOT_FOUND.name(), LocalDateTime.now());

        log.warn("{} Exception:{} Message:{}",
                response.getTimestamp(), exception.getClass().getSimpleName(),
                exception.getMessage());
        return response;
    }

    /**
     * Обработчик исключений приводящих к нарушению целостности данных.
     * 409 CONFLICT.
     * @param exception исключение {@link DataIntegrityViolationException}.
     * @return возвращает сообщение об ошибке {@link ErrorResponse}.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse conflictHandler(DataIntegrityViolationException exception) {
        ErrorResponse response = new ErrorResponse(null,
                exception.getMessage(),
                INTEGRITY_VIOLATION_MSG,
                HttpStatus.CONFLICT.name(), LocalDateTime.now());

        log.warn("{} Exception:{} Message:{}",
                response.getTimestamp(), exception.getClass().getSimpleName(),
                exception.getMessage());
        return response;
    }

    /**
     * Обработчик неизвестных исключений. Внутренняя ошибка сервера.
     * 500 INTERNAL_SERVER_ERROR.
     * @param exception исключение {@link Exception}.
     * @return возвращает сообщение об ошибке {@link ErrorResponse}.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse innerErrorHandler(Exception exception) {
        ErrorResponse response = new ErrorResponse(null,
                exception.getMessage(),
                INTERNAL_ERROR_MSG,
                HttpStatus.INTERNAL_SERVER_ERROR.name(), LocalDateTime.now());

        log.warn("{} Exception:{} Message:{}",
                response.getTimestamp(), exception.getClass().getSimpleName(),
                exception.getMessage());
        return response;
    }
}