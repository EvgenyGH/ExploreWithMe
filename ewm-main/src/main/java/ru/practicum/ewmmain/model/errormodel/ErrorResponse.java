package ru.practicum.ewmmain.model.errormodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Модель сообщения об ошибке.
 * @author Evgeny S
 */
@Getter
public class ErrorResponse {
    /**
     * Список стектрейсов или описания ошибок.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    StackTraceElement[] errors;

    /**
     * Сообщение об ошибке.
     */
    String message;

    /**
     * Общее описание причины ошибки.
     */
    String reason;

    /**
     * Код статуса HTTP-ответа.
     */
    String status;

    /**
     * Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss").
     */
    String timestamp;

    /**
     * Конструктор сообщения об ошибке
     * @param errors список стектрейсов или описания ошибок.
     * @param message сообщение.
     * @param reason причина.
     * @param status код статуса HTTP-ответа.
     * @param timestamp дата и время ошибки.
     */
    public ErrorResponse(StackTraceElement[] errors, String message, String reason,
                         String status, LocalDateTime timestamp) {
        this.errors = errors;
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
