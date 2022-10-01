package ru.practicum.ewmmain.utils.mapper;

import ru.practicum.ewmmain.model.event.dto.EventDtoShort;
import ru.practicum.ewmmain.model.compilation.Compilation;
import ru.practicum.ewmmain.model.compilation.dto.CompilationDto;

import java.util.List;

public class CompilationDtoMapper {
    public static CompilationDto toDto(Compilation compilation, List<EventDtoShort> events) {
        return new CompilationDto(events, compilation.getId(), compilation.getPinned(), compilation.getTitle());
    }
}
