package ru.practicum.ewmmain.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmain.model.event.location.dto.LocationDto;

import java.time.LocalDateTime;

/**
 * Dto события {@link ru.practicum.ewmmain.model.event.Event}.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.Event
 * @see ru.practicum.ewmmain.model.event.dto.EventDtoShort
 * @see ru.practicum.ewmmain.model.event.dto.EventNewDto
 * @see ru.practicum.ewmmain.model.event.dto.EventDto
 * @see ru.practicum.ewmmain.model.event.dto.EventUpdateAdminDto
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateAdminDto extends EventUpdateDto {
    /**
     * Аннотация.
     */
    private String annotation;

    /**
     * id категории событий {@link ru.practicum.ewmmain.model.event.category.Category}
     */
    private Integer category;

    /**
     * Описание.
     */
    private String description;

    /**
     * Дата проведения события.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    /**
     * Место события {@link LocationDto}.
     */
    private LocationDto location;

    /**
     * Платное/бесплатное событие.
     */
    private Boolean paid;

    /**
     * Лимит на количество участников.
     */
    private Integer participantLimit;

    /**
     * Необходимость модерации запросов на участие.
     */
    private Boolean requestModeration;

    /**
     * Заголовок.
     */
    private String title;
}
