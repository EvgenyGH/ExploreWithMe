package ru.practicum.ewmstats.service;

import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса статистики
 * @see StatServiceImpl
 * @author Evgeny S
 */
public interface StatService {

    /**
     * Сохранение информации об обращении к ресурсу.
     * @param hit - информация об обращении к ресурсу.
     */
    void save(EndpointHit hit);

    /**
     * Получение статистики обращений к ресурсу по фильтрам.
     * @param start начало периода.
     * @param end конец периода.
     * @param uris uri ресурса.
     * @param unique не учитывать повторные обращения к ресурсу с одного и того же ip (true).
     * @return - возвращает статистические данные об обращениях ресурсу {@link List}<{@link ViewStats}>
     */
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
