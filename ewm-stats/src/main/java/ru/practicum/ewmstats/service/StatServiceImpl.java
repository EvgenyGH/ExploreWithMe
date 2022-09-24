package ru.practicum.ewmstats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.model.ViewStats;
import ru.practicum.ewmstats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

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
        List<ViewStats> stats = repository.getStats(start, end, uris);

        if (unique && stats.size() != 0) {
            stats.forEach(vs -> vs.setHits(repository.countUniqueHits(start, end, vs.getUri(), vs.getApp())));
        }

        log.trace("{} For request start={} end={} unique={} uris={} response :: {}", LocalDateTime.now(),
                start, end, unique, uris, stats);

        return stats;
    }
}