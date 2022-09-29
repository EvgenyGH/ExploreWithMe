package ru.practicum.ewmmain.setlocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.setlocation.model.SetLocation;

public interface SetLocationRepository extends JpaRepository<SetLocation, Integer> {
}
