package ru.practicum.ewmmain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
