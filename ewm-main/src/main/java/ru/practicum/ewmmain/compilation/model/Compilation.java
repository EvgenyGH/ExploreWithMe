package ru.practicum.ewmmain.compilation.model;

import lombok.*;
import ru.practicum.ewmmain.event.model.event.Event;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private Integer id;

    @Column(nullable = false)
    private Boolean pinned;

    @Column(nullable = false, length = 100)
    private String title;

    @OneToMany
    @JoinTable(name = "compilation_event_connector",
            joinColumns = @JoinColumn (name = "compilation_id"),
            inverseJoinColumns = @JoinColumn (name = "event_id"))
    private List<Event> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compilation that = (Compilation) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id : 0;
    }
}
