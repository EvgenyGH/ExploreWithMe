package ru.practicum.ewmmain.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.compilation.model.CompilationDto;
import ru.practicum.ewmmain.compilation.model.CompilationNewDto;
import ru.practicum.ewmmain.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class CompilationController {
    private final CompilationService service;

    //Получение подборок событий.
    @GetMapping("/compilations")
    List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                         @RequestParam(required = false, defaultValue = "0") Integer from,
                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        return service.getCompilations(pinned, from, size);
    }

    //Получение подборки событий по его id.
    @GetMapping("/compilations/{compId}")
    CompilationDto getCompilationById(@PathVariable @Min(0) Integer compId) {
        return service.getCompilationById(compId);
    }

    //Добавление новой подборки.
    //compilationNewDto.pinned default = false.
    @PostMapping("/admin/compilations")
    CompilationDto addCompilation(@RequestBody @Valid CompilationNewDto compilationNewDto) {
        return service.addCompilation(compilationNewDto);
    }

    //Удаление подборки.
    @DeleteMapping("/admin/compilations/{compId}")
    void deleteCompilation(@PathVariable @Min(0) Integer compId) {
        service.deleteCompilation(compId);
    }

    //Удалить событие из подборки.
    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
    void deleteCompilationEvent(@PathVariable @Min(0) Integer compId,
                                @PathVariable @Min(0) Integer eventId) {
        service.deleteCompilationEvent(compId, eventId);
    }

    //Добавить событие в подборку.
    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
    void addCompilationEvent(@PathVariable @Min(0) Integer compId,
                             @PathVariable @Min(0) Integer eventId) {
        service.addCompilationEvent(compId, eventId);
    }

    //Открепить подборку на главной странице.
    @DeleteMapping("/admin/compilations/{compId}/pin")
    void unpinCompilation(@PathVariable @Min(0) Integer compId){
        service.unpinCompilation(compId);
    }

    //Закрепить подборку на главной странице.
    @PatchMapping("/admin/compilations/{compId}/pin")
    void pinCompilation(@PathVariable @Min(0) Integer compId){
        service.pinCompilation(compId);
    }

}
