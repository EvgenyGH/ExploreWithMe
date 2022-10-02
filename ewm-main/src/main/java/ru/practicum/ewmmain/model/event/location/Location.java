package ru.practicum.ewmmain.model.event.location;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс для хранения географических координат.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.event.location.dto.LocationDto
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "locations")
public class Location {
    /**
     * id локации.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer id;

    /**
     * Широта.
     */
    @Column(nullable = false)
    private Float latitude;

    /**
     * Долгота.
     */
    @Column(nullable = false)
    private Float longitude;

    /**
     * Equals по полю {@link #id}.
     * @return Equals по полю {@link #id}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return Objects.equals(id, location.id);
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
