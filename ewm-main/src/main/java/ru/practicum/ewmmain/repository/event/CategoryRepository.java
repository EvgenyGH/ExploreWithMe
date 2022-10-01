package ru.practicum.ewmmain.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.model.event.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
