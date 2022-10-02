package ru.practicum.ewmmain.model.event.category;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс для хранения категорий события.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.category.dto.CategoryDto
 * @see ru.practicum.ewmmain.model.event.category.dto.CategoryNewDto
 * @see ru.practicum.ewmmain.model.event.Event
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    /**
     * id категории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    /**
     * Название категории.
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * Equals по полю {@link #id}.
     * @return Equals по полю {@link #id}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return Objects.equals(id, category.id);
    }

    /**
     * HashCode по полю {@link #id}.
     * @return HashCode по полю {@link #id}.
     */
    @Override
    public int hashCode() {
        return id != null ? id : 0;
    }
}
