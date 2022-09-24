package ru.practicum.ewmstats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.model.ViewStats;
import ru.practicum.ewmstats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatsRepository repository;

    @Override
    public void save(EndpointHit hit) {
        hit.setId(0);
        hit = repository.save(hit);
        log.trace("{} EnpointHit id={} stored: {}", LocalDateTime.now(), hit.getId(), hit);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<ViewStats> stats;

        if (unique) {
            stats = repository.getStatsUnique(start, end, uris).stream()
                    .map(o -> new ViewStats(o.getApp(), o.getUri(), o.getHits())).collect(Collectors.toList());
        } else {
            stats = repository.getStats(start, end, uris);
        }

        log.trace("{} For request start={} end={} unique={} uris={} response :: {}", LocalDateTime.now(),
                start, end, unique, uris, stats);

        return stats;
    }
}