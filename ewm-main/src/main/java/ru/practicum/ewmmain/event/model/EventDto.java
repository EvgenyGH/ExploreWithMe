package ru.practicum.ewmmain.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmain.user.model.UserDtoShort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EventDto implements Serializable {
    @NotBlank
    @Size(max = 7000, min = 20)
    private String annotation;

    @NotNull
    private CategoryDto category;

    @NotNull
    @Min(0)
    private Integer confirmedRequests;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @NotBlank
    @Size(max = 7000, min = 20)
    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private UserDtoShort initiator;

    @NotNull
    private Location location;

    @NotNull
    private Boolean paid;

    @NotNull
    @Min(0)
    private Integer participantLimit;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    @NotNull
    private Boolean requestModeration;

    @NotNull
    private State state;

    @NotBlank
    @Size(max = 120, min = 3)
    private String title;

    @NotNull
    @Min(0)
    private Integer views;
}