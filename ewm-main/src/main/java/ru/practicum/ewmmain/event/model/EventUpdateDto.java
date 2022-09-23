package ru.practicum.ewmmain.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmain.event.validator.TwoHoursAfterNow;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateDto {
    @NotNull
    @Min(0)
    private Integer eventId;

    @NotBlank
    @Size(max = 7000, min = 20)
    private String annotation;

    @NotNull
    @Min(0)
    private Integer category;

    @NotBlank
    @Size(max = 7000, min = 20)
    private String description;

    @NotNull
    @TwoHoursAfterNow
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private Boolean paid;

    @NotNull
    @Min(0)
    private Integer participantLimit;

    @NotBlank
    @Size(max = 120, min = 3)
    private String title;
}
