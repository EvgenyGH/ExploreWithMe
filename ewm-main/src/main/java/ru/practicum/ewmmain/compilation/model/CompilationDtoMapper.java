package ru.practicum.ewmmain.compilation.model;

import ru.practicum.ewmmain.event.model.event.EventDtoShort;

import java.util.List;

public class CompilationDtoMapper {
    public static CompilationDto toDto(Compilation compilation, List<EventDtoShort> events) {
        return new CompilationDto(events, compilation.getId(), compilation.getPinned(), compilation.getTitle());
    }
}
