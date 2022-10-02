package ru.practicum.ewmmain.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmmain.model.event.location.Location;

import java.util.Optional;

/**
 * Репозиторий класса {@link Location}
 * @author Evgeny S
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query(value = "SELECT l FROM Location l " +
            "WHERE l.latitude = ?1 " +
            "AND l.longitude = ?2")
    Optional<Location> getByLatAndLon(Float latitude, Float longitude);
}
