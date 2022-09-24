package ru.practicum.ewmstats.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ViewStats {
    //Идентификатор сервиса для которого записывается информация.
    private String app;

    //URI для которого был осуществлен запрос.
    private String uri;

    //Количество просмотров
    private Long hits;
}