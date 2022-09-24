package ru.practicum.ewmmain.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.ewmmain.errormodel.ErrorResponse;
import ru.practicum.ewmmain.exception.CategoryNotFoundException;
import ru.practicum.ewmmain.user.exception.UserNotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice({"ru.practicum.ewmmain"})
@Slf4j
public class EwmExceptionHandler {
    private static final String INTEGRITY_VIOLATION_MSG = "Integrity constraint has been violated";
    private static final String INTERNAL_ERROR_MSG = "Error occurred";
    private static final String BAD_REQUEST_MSG = "Bad request";
    private static final String NOT_FOUND_MSG = "The required object was not found";
    private static final String FORBIDDEN_MSG = "Only pending or canceled events can be changed";

    //400 BAD_REQUEST Запрос составлен с ошибкой
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

    /*//403 FORBIDDEN Не выполнены условия для совершения операции
    @ExceptionHandler()
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse forbiddenHandler(DataIntegrityViolationException exception) {
        ErrorResponse response = new ErrorResponse(exception.getStackTrace(),
                exception.getMessage(),
                FORBIDDEN_MSG,
                HttpStatus.FORBIDDEN.name(), LocalDateTime.now());

        log.warn("{} Exception:{} Message:{}",
                response.getTimestamp(), exception.getClass().getSimpleName(),
                exception.getMessage());
        return response;
    }*/

    //404 NOT_FOUND Объект не найден
    @ExceptionHandler({EmptyResultDataAccessException.class, CategoryNotFoundException.class,
            UserNotFoundException.class})
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

    //409 CONFLICT Запрос приводит к нарушению целостности данных
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

    //500 INTERNAL_SERVER_ERROR Внутренняя ошибка сервера
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