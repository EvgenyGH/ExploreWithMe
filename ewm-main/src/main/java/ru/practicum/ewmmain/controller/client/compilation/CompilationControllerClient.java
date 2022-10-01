package ru.practicum.ewmmain.controller.client.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.model.compilation.dto.CompilationDto;
import ru.practicum.ewmmain.service.compilation.CompilationService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/compilations")
public class CompilationControllerClient {
    private final CompilationService service;

    //Получение подборок событий.
    @GetMapping
    List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                         @RequestParam(required = false, defaultValue = "0") Integer from,
                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        return service.getCompilations(pinned, from, size);
    }

    //Получение подборки событий по его id.
    @GetMapping("/{compId}")
    CompilationDto getCompilationById(@PathVariable @Min(0) Integer compId) {
        return service.getCompilationById(compId);
    }
}
