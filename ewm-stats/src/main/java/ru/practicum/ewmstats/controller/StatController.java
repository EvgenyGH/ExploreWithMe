package ru.practicum.ewmstats.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.model.ViewStats;
import ru.practicum.ewmstats.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class StatController {
    private final StatService service;

    //Сохранение информации о том, что к эндпоинту был запрос
    @PostMapping("/hit")
    void save(@RequestBody @Valid EndpointHit hit) {
        service.save(hit);
    }

    //Получение статистики по посещениям.
    @GetMapping("/stats")
    List<ViewStats> getStats(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                             @RequestParam(required = false) List<String> uris,
                             @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}
