package ru.practicum.ewmmain.model.compilation.dto;

import lombok.*;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;

import java.util.List;

/**
 * Dto объекта Compilation
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.compilation.Compilation
 * @see CompilationNewDto
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    /**
     * События.
     */
    private List<EventDtoShort> events;
    /**
     * Id подборки.
     */
    private Integer id;
    /**
     * Закрепление события на главной странице.
     */
    private Boolean pinned;
    /**
     * Заголовок.
     */
    private String title;
}