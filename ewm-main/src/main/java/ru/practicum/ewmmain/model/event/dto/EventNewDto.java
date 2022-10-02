package ru.practicum.ewmmain.model.event.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewmmain.model.event.location.dto.LocationDto;
import ru.practicum.ewmmain.utils.validator.TwoHoursAfterNow;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Dto события {@link ru.practicum.ewmmain.model.event.Event}.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.Event
 * @see ru.practicum.ewmmain.model.event.dto.EventDtoShort
 * @see ru.practicum.ewmmain.model.event.dto.EventDto
 * @see ru.practicum.ewmmain.model.event.dto.EventUpdateDto
 * @see ru.practicum.ewmmain.model.event.dto.EventUpdateAdminDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventNewDto {
    /**
     * Аннотация.
     */
    @NotBlank
    @Size(max = 2000, min = 20)
    private String annotation;

    /**
     * Id категории события
     * @see ru.practicum.ewmmain.model.event.category.Category
     */
    @NotNull
    @Min(0)
    private Integer category;

    /**
     * Описание.
     */
    @NotBlank
    @Length(max = 7000, min = 20)
    private String description;

    /**
     * Дата проведения события.
     */
    @NotNull
    @TwoHoursAfterNow
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    /**
     * Место события {@link LocationDto}.
     */
    @NotNull
    private LocationDto location;

    /**
     * Платное/бесплатное событие.
     */
    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean paid = false;

    /**
     * Лимит на количество участников.
     */
    @Min(0)
    @JsonSetter(nulls = Nulls.SKIP)
    private Integer participantLimit = 0;

    /**
     * Необходимость модерации запросов на участие.
     */
    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean requestModeration = false;

    /**
     * Заголовок.
     */
    @NotBlank
    @Size(max = 120, min = 3)
    private String title;
}
