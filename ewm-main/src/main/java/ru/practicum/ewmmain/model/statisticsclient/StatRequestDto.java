package ru.practicum.ewmmain.model.statisticsclient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Dto отправки информации на сервис статистики.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.utils.client.StatisticsClient
 * @see ru.practicum.ewmmain.utils.client.StatisticsClientImpl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatRequestDto {
    /**
     * id
     */
    private Integer id;

    /**
     * Имя приложения.
     */
    private String app;


    /**
     * uri ресурса.
     */
    private String uri;

    /**
     * ip адрес от которого пришел запрос.
     */
    private String ip;

    /**
     * Дата и время запроса.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}