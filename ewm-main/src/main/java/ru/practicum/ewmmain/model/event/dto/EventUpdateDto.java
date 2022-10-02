package ru.practicum.ewmmain.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmain.utils.validator.TwoHoursAfterNow;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class EventUpdateDto {
    /**
     * id события.
     */
    @NotNull
    @Min(0)
    private Integer eventId;

    /**
     * Аннотация.
     */
    @Size(max = 7000, min = 20)
    private String annotation;

    /**
     * id категории события {@link ru.practicum.ewmmain.model.event.category.Category}
     */
    @Min(0)
    private Integer category;

    /**
     * Описание.
     */
    @Size(max = 7000, min = 20)
    private String description;

    /**
     * Дата проведения события.
     */
    @TwoHoursAfterNow
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    /**
     * Платное/бесплатное событие.
     */
    private Boolean paid;

    /**
     * Лимит на количество участников.
     */
    @Min(0)
    private Integer participantLimit;

    /**
     * Заголовок.
     */
    @Size(max = 120, min = 3)
    private String title;
}