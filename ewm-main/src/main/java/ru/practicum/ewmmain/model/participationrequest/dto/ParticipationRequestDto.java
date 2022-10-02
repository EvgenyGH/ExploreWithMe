package ru.practicum.ewmmain.model.participationrequest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmain.model.participationrequest.Status;

import java.time.LocalDateTime;

/**
 * Dto заявок на участие в событии {@link ru.practicum.ewmmain.model.event.Event}
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.participationrequest.ParticipationRequest
 * @see ru.practicum.ewmmain.model.event.Event
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ParticipationRequestDto {
    /**
     * id заявки.
     */
    private Integer id;

    /**
     * id события.
     */
    private Integer event;


    /**
     * Дата создания.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    /**
     * Автор запроса.
     */
    private Integer requester;

    /**
     * Статус запроса.
     */
    private Status status;
}
