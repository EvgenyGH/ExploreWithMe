package ru.practicum.ewmmain.model.compilation.dto;

import lombok.*;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private List<EventDtoShort> events;
    private Integer id;
    private Boolean pinned;
    private String title;
}