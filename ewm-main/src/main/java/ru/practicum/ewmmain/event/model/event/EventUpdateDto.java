package ru.practicum.ewmmain.event.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmain.event.validator.TwoHoursAfterNow;

import javax.validation.constraints.Min;
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

    @Size(max = 7000, min = 20)
    private String annotation;

    @Min(0)
    private Integer category;

    @Size(max = 7000, min = 20)
    private String description;

    @TwoHoursAfterNow
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Boolean paid;

    @Min(0)
    private Integer participantLimit;

    @Size(max = 120, min = 3)
    private String title;
}
