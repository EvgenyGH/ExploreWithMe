package ru.practicum.ewmmain.model.statisticsclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto получения информации от сервиса статистики.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.utils.client.StatisticsClient
 * @see ru.practicum.ewmmain.utils.client.StatisticsClientImpl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatResponseDto {
    /**
     * Имя приложения.
     */
    private String app;

    /**
     * uri ресурса.
     */
    private String uri;

    /**
     * Количество просмотров ресурса.
     */
    private Integer hits;
}