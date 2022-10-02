package ru.practicum.ewmmain.model.event;

import lombok.*;
import ru.practicum.ewmmain.model.event.category.Category;
import ru.practicum.ewmmain.model.event.location.Location;
import ru.practicum.ewmmain.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс хранения события.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.dto.EventDto
 * @see ru.practicum.ewmmain.model.event.dto.EventDtoShort
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    /**
     * id события.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer id;

    /**
     * Аннотация.
     */
    @Column(nullable = false, length = 2000)
    private String annotation;

    /**
     * Дата создания.
     */
    @Column(nullable = false)
    private LocalDateTime created;

    /**
     * Описание.
     */
    @Column(nullable = false, length = 7000)
    private String description;

    /**
     * Дата проведения события.
     */
    @Column(nullable = false)
    private LocalDateTime eventDate;

    /**
     * Инициатор события.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    private User initiator;

    /**
     * Место события {@link Location}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     * Платное/бесплатное событие.
     */
    @Column(nullable = false)
    private Boolean paid;

    /**
     * Лимит на количество участников.
     */
    @Column(name = "participant_limit", nullable = false)
    private Integer participantLimit;

    /**
     * Дата публикации события.
     */
    @Column(nullable = false)
    private LocalDateTime published;

    /**
     * Необходимость модерации запросов на участие.
     */
    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;

    /**
     * Состояние.
     */
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private State state;

    /**
     * Заголовок.
     */
    @Column(nullable = false, length = 50)
    private String title;

    /**
     * Категория.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Equals по полю {@link #id}.
     * @return Equals по полю {@link #id}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return Objects.equals(id, event.id);
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