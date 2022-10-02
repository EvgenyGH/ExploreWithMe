package ru.practicum.ewmmain.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmain.model.event.category.dto.CategoryDto;
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
 * @see ru.practicum.ewmmain.model.event.dto.EventDto
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
public class EventDtoShort {
    /**
     * Аннотация.
     */
    @NotBlank
    @Size(max = 2000, min = 20)
    private String annotation;

    /**
     * Категория {@link CategoryDto}.
     */
    @NotNull
    private CategoryDto category;

    /**
     * Количество подтвержденных заявок на участие.
     */
    @NotNull
    @Min(0)
    private Integer confirmedRequests;

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
     * Инициатор события {@link UserShortDto}
     */
    @NotNull
    private UserShortDto initiator;

    /**
     * Платное/бесплатное событие.
     */
    @NotNull
    private Boolean paid;

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