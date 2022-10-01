package ru.practicum.ewmstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий сущности {@link EndpointHit}
 * @author Evgeny S
 */
public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {

    /**
     * Получение статистики обращений к ресурсам по фильтрам.
     * @param start начало периода.
     * @param end конец периода.
     * @param uris uri ресурсов.
     * @return возвращает статистическую информацию по обращениям к ресурсам {@link List}<{@link ViewStats}>
     */
    @Query("SELECT new ru.practicum.ewmstats.model.ViewStats(e.app, e.uri, count(e.ip)) " +
            "FROM EndpointHit as e " +
            "WHERE e.timestamp BETWEEN ?1 AND ?2 AND " +
            "e.uri IN ?3 " +
            "GROUP BY e.uri, e.app")
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    /**
     * Получение статистики обращений к ресурсам по фильтрам без учета повторных обращений
     * к ресурсу с одного и того же ip.
     * @param start начало периода.
     * @param end конец периода.
     * @param uris uri ресурсов.
     * @return возвращает статистическую информацию по обращениям к ресурсам {@link List}<{@link ViewStats}>
     */
    @Query(value = "SELECT new ru.practicum.ewmstats.model.ViewStats(e.app, e.uri, count(distinct e.ip)) " +
            "FROM EndpointHit as e " +
            "WHERE e.timestamp BETWEEN ?1 AND ?2 AND " +
            "e.uri IN ?3 " +
            "GROUP BY e.uri, e.app")
    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    /**
     * Получение статистики обращений ко всем ресурсам по фильтрам.
     * @param start начало периода.
     * @param end конец периода.
     * @return возвращает статистическую информацию по обращениям к ресурсам {@link List}<{@link ViewStats}>
     */
    @Query("SELECT new ru.practicum.ewmstats.model.ViewStats(e.app, e.uri, count(e.ip)) " +
            "FROM EndpointHit as e " +
            "WHERE e.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY e.uri, e.app")
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end);

    /**
     * Получение статистики обращений ко всем ресурсам по фильтрам без учета повторных обращений
     * к ресурсу с одного и того же ip.
     * @param start начало периода.
     * @param end конец периода.
     * @return возвращает статистическую информацию по обращениям к ресурсам {@link List}<{@link ViewStats}>
     */
    @Query(value = "SELECT new ru.practicum.ewmstats.model.ViewStats(e.app, e.uri, count(distinct e.ip)) " +
            "FROM EndpointHit as e " +
            "WHERE e.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY e.uri, e.app")
    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end);
}
