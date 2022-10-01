package ru.practicum.ewmmain.model.event.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    @NotNull
    @PositiveOrZero
    private Float lat;

    @NotNull
    @PositiveOrZero
    private Float lon;
}
