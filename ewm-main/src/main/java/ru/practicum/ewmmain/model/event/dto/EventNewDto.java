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

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventNewDto {
    @NotBlank
    @Size(max = 2000, min = 20)
    private String annotation;

    @NotNull
    @Min(0)
    private Integer category;

    @NotBlank
    @Length(max = 7000, min = 20)
    private String description;

    @NotNull
    @TwoHoursAfterNow
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private LocationDto location;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean paid = false;

    @Min(0) //Значение 0 - означает отсутствие ограничения
    @JsonSetter(nulls = Nulls.SKIP)
    private Integer participantLimit = 0;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean requestModeration = false;

    @NotBlank
    @Size(max = 120, min = 3)
    private String title;
}
