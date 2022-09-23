package ru.practicum.ewmmain.event.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private Float latitude;

    @NotNull
    @Column(nullable = false)
    private Float longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id : 0;
    }
}
