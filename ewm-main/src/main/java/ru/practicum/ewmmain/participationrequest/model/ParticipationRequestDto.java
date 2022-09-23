package ru.practicum.ewmmain.participationrequest.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ParticipationRequestDto {
    private Integer id;

    private Integer event;

    private LocalDateTime created;

    private Integer requester;

    private Status status;
}
