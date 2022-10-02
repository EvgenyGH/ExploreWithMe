package ru.practicum.ewmmain.model.setlocation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewmmain.model.setlocation.SetLocation;

import javax.validation.constraints.*;

/**
 * Dto локации {@link SetLocation}.
 *
 * @author Evgeny S
 * @see SetLocation
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetLocationDto {
    /**
     * id локации.
     */
    private Integer id;

    /**
     * Название локации.
     */
    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    /**
     * Описание локации.
     */
    @NotBlank
    @Length(min = 10, max = 1000)
    private String description;

    /**
     * Широта.
     */
    @NotNull
    @Min(-90)
    @Max(90)
    private Float latitude;

    /**
     * Долгота.
     */
    @NotNull
    @Min(-180)
    @Max(180)
    private Float longitude;

    /**
     * Радиус.
     */
    @NotNull
    @PositiveOrZero
    private Float radius;
}
