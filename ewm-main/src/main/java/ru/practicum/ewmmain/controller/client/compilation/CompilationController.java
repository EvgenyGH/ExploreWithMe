package ru.practicum.ewmmain.controller.client.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.controller.admin.compilation.CompilationControllerAdm;
import ru.practicum.ewmmain.model.compilation.dto.CompilationDto;
import ru.practicum.ewmmain.service.compilation.CompilationService;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Публичный контроллер подборок({@link ru.practicum.ewmmain.model.compilation.Compilation})
 * @author Evgeny S
 * @see CompilationControllerAdm
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/compilations")
public class CompilationController {
    /**
     * Сервис для работы с подборками событий.
     */
    private final CompilationService service;

    /**
     * Получение подборок событий.
     * @param pinned подборка закреплена на главной странице.
     * @param from начальный элемент выборки.
     * @param size размер выборки.
     * @return возвращает выборку событий {@link List}<{@link CompilationDto}>
     * в соответствии с заданными фильтрами.
     */
    @GetMapping
    List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                         @RequestParam(required = false, defaultValue = "0") Integer from,
                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        return service.getCompilations(pinned, from, size);
    }

    /**
     * Получение подборки событий по его id.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     * @return возвращает подборку событий {@link CompilationDto} с заданным id.
     */
    @GetMapping("/{compId}")
    CompilationDto getCompilationById(@PathVariable @Min(0) Integer compId) {
        return service.getCompilationById(compId);
    }
}
