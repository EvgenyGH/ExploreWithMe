package ru.practicum.ewmmain.compilation.model;

import lombok.*;
import ru.practicum.ewmmain.event.model.event.EventDtoShort;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto implements Serializable {
    private List<EventDtoShort> events;
    private Integer id;
    private Boolean pinned;
    private String title;
}