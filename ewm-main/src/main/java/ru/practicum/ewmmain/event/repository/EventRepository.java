package ru.practicum.ewmmain.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmmain.event.model.event.Event;
import ru.practicum.ewmmain.event.model.event.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByInitiatorId(Integer initiatorId, Pageable pageable);

    @Query(value = "SELECT e FROM Event e " +
            "WHERE e.initiator.id IN ?1 " +
            "AND e.state IN ?2 " +
            "AND e.category.id IN ?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5")
    List<Event> getEventsAdmin(List<Integer> userIds, List<State> states, List<Integer> categoryIds,
                               LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    //Только опубликованные события.
    @Query(value = "SELECT e FROM Event e " +
            "WHERE (lower(e.annotation) LIKE concat('%', ?1, '%') " +
            "OR lower(e.description) LIKE concat('%', ?1, '%')) " +
            "AND e.category.id IN ?2 " +
            "AND e.paid = ?3 " +
            "AND e.eventDate BETWEEN ?4 AND ?5 " +
            "AND e.state = 'PUBLISHED'")
    List<Event> getPublishedEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, PageRequest event_date);

}
