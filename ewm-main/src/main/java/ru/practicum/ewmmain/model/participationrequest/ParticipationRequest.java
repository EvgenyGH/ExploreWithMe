package ru.practicum.ewmmain.model.participationrequest;

import lombok.*;
import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс хранения заявок на участие в событии.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.participationrequest.dto.ParticipationRequestDto
 * @see Event
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "participation_requests")
public class ParticipationRequest {
    /**
     * id заявки на участие.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer id;

    /**
     * Событие.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /**
     * Дата создания заявки.
     */
    @Column(nullable = false)
    private LocalDateTime created;

    /**
     * Автор заявки.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    /**
     * Статус заявки.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    /**
     * Equals по полю {@link #id}.
     * @return Equals по полю {@link #id}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipationRequest that = (ParticipationRequest) o;

        return Objects.equals(id, that.id);
    }

    /**
     * HashCode по полю {@link #id}.
     * @return HashCode по полю {@link #id}.
     */
    @Override
    public int hashCode() {
        return id != null ? id : 0;
    }
}
