package ru.practicum.ewmmain.controller.admin.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.model.compilation.dto.CompilationDto;
import ru.practicum.ewmmain.model.compilation.dto.CompilationNewDto;
import ru.practicum.ewmmain.service.compilation.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class CompilationControllerAdm {
    private final CompilationService service;

    //Добавление новой подборки.
    //compilationNewDto.pinned default = false.
    @PostMapping
    CompilationDto addCompilation(@RequestBody @Valid CompilationNewDto compilationNewDto) {
        return service.addCompilation(compilationNewDto);
    }

    //Удаление подборки.
    @DeleteMapping("/{compId}")
    void deleteCompilation(@PathVariable @Min(0) Integer compId) {
        service.deleteCompilation(compId);
    }

    //Удалить событие из подборки.
    @DeleteMapping("/{compId}/events/{eventId}")
    void deleteCompilationEvent(@PathVariable @Min(0) Integer compId,
                                @PathVariable @Min(0) Integer eventId) {
        service.deleteCompilationEvent(compId, eventId);
    }

    //Добавить событие в подборку.
    @PatchMapping("/{compId}/events/{eventId}")
    void addCompilationEvent(@PathVariable @Min(0) Integer compId,
                             @PathVariable @Min(0) Integer eventId) {
        service.addCompilationEvent(compId, eventId);
    }

    //Открепить подборку на главной странице.
    @DeleteMapping("/{compId}/pin")
    void unpinCompilation(@PathVariable @Min(0) Integer compId) {
        service.unpinCompilation(compId);
    }

    //Закрепить подборку на главной странице.
    @PatchMapping("/{compId}/pin")
    void pinCompilation(@PathVariable @Min(0) Integer compId) {
        service.pinCompilation(compId);
    }
}
