package ru.practicum.ewmmain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.event.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
