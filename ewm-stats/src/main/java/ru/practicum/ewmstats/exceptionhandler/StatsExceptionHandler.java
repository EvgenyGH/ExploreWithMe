package ru.practicum.ewmstats.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice("ru.practicum.ewmstats")
@Slf4j
public class StatsExceptionHandler {

    //Сервис не возвращает сообщения об ошибках
    @ExceptionHandler
    void everyExceptionHandler(Exception exception) {
        log.warn("{} Exception:{} Message:{}", LocalDateTime.now(),
                exception.getClass().getSimpleName(), exception.getMessage());
    }
}
