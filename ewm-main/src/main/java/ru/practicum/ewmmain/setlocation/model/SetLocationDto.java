package ru.practicum.ewmmain.setlocation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetLocationDto {
    private Integer id;

    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    @NotBlank
    @Length(min = 10, max = 1000)
    private String description;

    @NotNull
    @Min(-90)
    @Max(90)
    private Float latitude;

    @NotNull
    @Min(-180)
    @Max(180)
    private Float Longitude;

    @NotNull
    @PositiveOrZero
    private Float radius;
}
