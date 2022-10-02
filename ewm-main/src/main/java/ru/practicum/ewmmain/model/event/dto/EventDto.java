package ru.practicum.ewmmain.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.event.category.dto.CategoryDto;
import ru.practicum.ewmmain.model.event.location.dto.LocationDto;
import ru.practicum.ewmmain.model.user.dto.UserShortDto;

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
 * @see ru.practicum.ewmmain.model.event.dto.EventNewDto
 * @see ru.practicum.ewmmain.model.event.dto.EventUpdateDto
 * @see ru.practicum.ewmmain.model.event.dto.EventUpdateAdminDto
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    /**
     * Аннотация.
     */
    @NotBlank
    @Size(max = 7000, min = 20)
    private String annotation;

    /**
     * Категория события.
     */
    @NotNull
    private CategoryDto category;

    /**
     * Подтвержденные заявки на участие.
     */
    @NotNull
    @Min(0)
    private Integer confirmedRequests;

    /**
     * Дата создания.
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    /**
     * Описание.
     */
    @NotBlank
    @Size(max = 7000, min = 20)
    private String description;

    /**
     * Дата проведения события.
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    /**
     * id события.
     */
    @NotNull
    @Min(0)
    private Integer id;

    /**
     * Инициатор события {@link UserShortDto}.
     */
    @NotNull
    private UserShortDto initiator;

    /**
     * Место события {@link LocationDto}.
     */
    @NotNull
    private LocationDto location;

    /**
     * Платное/бесплатное событие.
     */
    @NotNull
    private Boolean paid;

    /**
     * Лимит на участников.
     */
    @NotNull
    @Min(0)
    private Integer participantLimit;

    /**
     * Дата публикации события.
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    /**
     * Необходимость модерации запросов на участие.
     */
    @NotNull
    private Boolean requestModeration;

    /**
     * Состояние.
     */
    @NotNull
    private State state;

    /**
     * Заголовок.
     */
    @NotBlank
    @Size(max = 120, min = 3)
    private String title;

    /**
     * Количество просмотров события.
     */
    @NotNull
    @Min(0)
    private Integer views;
}