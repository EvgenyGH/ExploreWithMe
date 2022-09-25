package ru.practicum.ewmmain.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.event.model.event.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByInitiatorId(Integer initiatorId, Pageable pageable);
}
