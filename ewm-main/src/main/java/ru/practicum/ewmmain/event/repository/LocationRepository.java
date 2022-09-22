package ru.practicum.ewmmain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.event.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
