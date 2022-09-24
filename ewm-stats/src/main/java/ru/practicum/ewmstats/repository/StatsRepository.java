package ru.practicum.ewmstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {
    @Query("SELECT new java.lang.Long(count(distinct e.ip)) " +
            "FROM EndpointHit as e " +
            "WHERE e.timestamp BETWEEN ?1 AND ?2 AND " +
            "e.uri =?3 AND " +
            "e.app =?4 ")
    Long countUniqueHits(LocalDateTime start, LocalDateTime end, String uri, String app);

    @Query("SELECT new ru.practicum.ewmstats.model.ViewStats(e.app, e.uri, count(e.uri)) " +
            "FROM EndpointHit as e " +
            "WHERE e.timestamp BETWEEN ?1 AND ?2 AND " +
            "e.uri IN ?3 " +
            "GROUP BY e.uri, e.app")
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}
