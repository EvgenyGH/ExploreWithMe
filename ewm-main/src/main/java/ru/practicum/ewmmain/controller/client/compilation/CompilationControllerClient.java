package ru.practicum.ewmmain.controller.client.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmmain.model.compilation.dto.CompilationDto;
import ru.practicum.ewmmain.service.compilation.CompilationService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class CompilationControllerClient {
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
}
