package ru.practicum.ewmmain.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.model.compilation.dto.CompilationDto;
import ru.practicum.ewmmain.model.compilation.dto.CompilationNewDto;
import ru.practicum.ewmmain.service.compilation.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Закрытый контроллер подборок({@link ru.practicum.ewmmain.model.compilation.Compilation})
 * для администратора.
 * @author Evgeny S
 * @see CompilationController
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class CompilationControllerAdm {
    private final CompilationService service;

    /**
     * Добавление новой подборки.
     * @param compilationNewDto экземпляр класса данных для создания новой подборки. Значение
     *                          поля pinned по умолчанию false.
     * @return возвращает созданную подборку {@link CompilationDto}
     */
    @PostMapping
    CompilationDto addCompilation(@RequestBody @Valid CompilationNewDto compilationNewDto) {
        return service.addCompilation(compilationNewDto);
    }

    /**
     * Удаление подборки.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     */
    @DeleteMapping("/{compId}")
    void deleteCompilation(@PathVariable @Min(0) Integer compId) {
        service.deleteCompilation(compId);
    }

    /**
     * Удалить событие из подборки.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     * @param eventId Id удаляемого события {@link ru.practicum.ewmmain.model.event.Event}
     */
    @DeleteMapping("/{compId}/events/{eventId}")
    void deleteCompilationEvent(@PathVariable @Min(0) Integer compId,
                                @PathVariable @Min(0) Integer eventId) {
        service.deleteCompilationEvent(compId, eventId);
    }

    /**
     * Добавить событие в подборку.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     * @param eventId Id добавляемого события {@link ru.practicum.ewmmain.model.event.Event}
     */
    @PatchMapping("/{compId}/events/{eventId}")
    void addCompilationEvent(@PathVariable @Min(0) Integer compId,
                             @PathVariable @Min(0) Integer eventId) {
        service.addCompilationEvent(compId, eventId);
    }

    /**
     * Открепить подборку на главной странице.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     */
    @DeleteMapping("/{compId}/pin")
    void unpinCompilation(@PathVariable @Min(0) Integer compId) {
        service.unpinCompilation(compId);
    }

    /**
     * Закрепить подборку на главной странице.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     */
    @PatchMapping("/{compId}/pin")
    void pinCompilation(@PathVariable @Min(0) Integer compId) {
        service.pinCompilation(compId);
    }
}
