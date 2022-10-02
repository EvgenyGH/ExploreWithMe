package ru.practicum.ewmmain.model.event.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
/**
 * Dto класса географических координат {@link ru.practicum.ewmmain.model.event.location.Location}.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.location.Location
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    /**
     * Широта.
     */
    @NotNull
    @PositiveOrZero
    private Float lat;

    /**
     * Долгота.
     */
    @NotNull
    @PositiveOrZero
    private Float lon;
}
