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

/**
 * Контроллер сервера статистики.
 * Сохраняет и возвращает по запросу статистику обращений к ресурсам.
 * @author Evgeny S
 */
@RestController
@RequiredArgsConstructor
@Validated
public class StatController {
    /**
     * Сервис сервера статистики.
     * @see StatService
     */
    private final StatService service;

    /**
     * Сохранение информации об обращении к ресурсу.
     * @param hit - информация об обращении к ресурсу.
     */
    @PostMapping("/hit")
    void save(@RequestBody @Valid EndpointHit hit) {
        service.save(hit);
    }

    /**
     * Получение статистики обращений к ресурсу по фильтрам.
     * @param start начало периода.
     * @param end конец периода.
     * @param uris uri ресурса.
     * @param unique не учитывать повторные обращения к ресурсу с одного и того же ip (true).
     * @return - возвращает статистические данные об обращениях ресурсу {@link List}<{@link ViewStats}>
     */
    @GetMapping("/stats")
    List<ViewStats> getStats(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                             LocalDateTime start,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                             LocalDateTime end,
                             @RequestParam(required = false) List<String> uris,
                             @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}
