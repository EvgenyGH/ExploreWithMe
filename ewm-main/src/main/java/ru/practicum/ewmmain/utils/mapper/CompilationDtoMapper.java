package ru.practicum.ewmmain.utils.mapper;

import ru.practicum.ewmmain.model.compilation.Compilation;
import ru.practicum.ewmmain.model.compilation.dto.CompilationDto;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;

import java.util.List;

/**
 * Маппер класса {@link Compilation}
 * @author Evgeny S
 */
public class CompilationDtoMapper {
    /**
     * Маппер в класс {@link CompilationDto}
     * @param compilation экземпляр класса для конвертации.
     * @param events события включенные в подборку.
     * @return возвращает конвертированный в класс {@link CompilationDto} экземпляр.
     */
    public static CompilationDto toDto(Compilation compilation, List<EventDtoShort> events) {
        return new CompilationDto(events, compilation.getId(), compilation.getPinned(), compilation.getTitle());
    }
}
