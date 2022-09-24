package ru.practicum.ewmstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.model.ViewStats;
import ru.practicum.ewmstats.model.ViewStatsProj;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {

    @Query("SELECT new ru.practicum.ewmstats.model.ViewStats(e.app, e.uri, count(e.uri)) " +
            "FROM EndpointHit as e " +
            "WHERE e.timestamp BETWEEN ?1 AND ?2 AND " +
            "e.uri IN ?3 " +
            "GROUP BY e.uri, e.app")
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT app, uri, count_unique (app, uri, ?1, ?2) as hits " +
            "FROM statistics as s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 AND " +
            "s.uri IN ?3 " +
            "GROUP BY s.uri, s.app", nativeQuery = true)
    List<ViewStatsProj> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}
