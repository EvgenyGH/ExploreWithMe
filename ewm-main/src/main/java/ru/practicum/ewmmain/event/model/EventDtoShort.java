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
public class EventDtoShort implements Serializable {
    private Integer id;

    @NotBlank
    @Size(max = 2000, min = 20)
    private String annotation;

    @NotNull
    private CategoryDto category;

    @NotNull
    @Min(0)
    private Integer confirmedRequests;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private UserDtoShort initiator;

    @NotNull
    private Boolean paid;

    @NotBlank
    @Size(max = 120, min = 3)
    private String title;

    @NotNull
    @Min(0)
    private Integer views;
}