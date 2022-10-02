package ru.practicum.ewmmain.repository.setlocation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.model.setlocation.SetLocation;

/**
 * Репозиторий сущности {@link SetLocation}
 *
 * @author Evgeny S
 */
public interface SetLocationRepository extends JpaRepository<SetLocation, Integer> {
}
