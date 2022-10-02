package ru.practicum.ewmmain.setlocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.setlocation.model.SetLocation;

/**
 * Репозиторий сущности {@link ru.practicum.ewmmain.setlocation.model.SetLocation}
 *
 * @author Evgeny S
 */
public interface SetLocationRepository extends JpaRepository<SetLocation, Integer> {
}
