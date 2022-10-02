package ru.practicum.ewmmain.setlocation.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Заданная администратором локация.
 *
 * @author Evgeny S
 * @see SetLocationDto
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "set_locations")
public class SetLocation {
    /**
     * id локации.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loc_id")
    private Integer id;

    /**
     * Название локации.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Описание локации.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Широта.
     */
    @Column(nullable = false)
    private Float latitude;

    /**
     * Долгота.
     */
    @Column(nullable = false)
    private Float Longitude;

    /**
     * Радиус.
     */
    @Column(nullable = false)
    private Float radius;

    /**
     * Equals по полю {@link #id}.
     * @return Equals по полю {@link #id}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetLocation that = (SetLocation) o;

        return Objects.equals(id, that.id);
    }

    /**
     * HashCode по полю {@link #id}.
     * @return HashCode по полю {@link #id}.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
