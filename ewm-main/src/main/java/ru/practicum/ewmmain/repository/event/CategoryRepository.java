package ru.practicum.ewmmain.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.model.event.category.Category;

/**
 * Репозиторий класса {@link Category}
 * @author Evgeny S
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
