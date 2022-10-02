package ru.practicum.ewmstats.model;

import lombok.*;

/**
 * Класс подборки статистической информации.
 * @author Evgeny S
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ViewStats {
    /**
     * Идентификатор сервиса обратившегося к ресурсу.
     */
    private String app;

    /**
     * URI по которому выполнено обращение к ресурсу.
     */
    private String uri;

    /**
     * Количество обращений к ресурсу
     */
    private Long hits;
}