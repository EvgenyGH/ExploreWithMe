package ru.practicum.ewmmain.errormodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ErrorResponse {
    //Список стектрейсов или описания ошибок
    List<StackTraceElement> errors;

    //Сообщение об ошибке
    String message;

    //Общее описание причины ошибки
    String reason;

    //Код статуса HTTP-ответа
    String status;

    //Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;

    public ErrorResponse(List<StackTraceElement> errors, String message, String reason,
                         String status, LocalDateTime timestamp) {
        this.errors = errors;
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
        this.timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
