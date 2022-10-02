package ru.practicum.ewmmain.model.user;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс пользователя.
 * @author Evgeny S
 * @see ru.practicum.ewmmain.model.user.dto.UserNewDto
 * @see ru.practicum.ewmmain.model.user.dto.UserShortDto
 * @see ru.practicum.ewmmain.model.user.dto.UserDto
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    /**
     * id пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    /**
     * Имя пользователя.
     */
    @Column(length = 50, nullable = false)
    private String name;

    /**
     * Адрес электронной почты пользователя.
     */
    @Column(length = 50, nullable = false)
    private String email;

    /**
     * Equals по полю {@link #id}.
     * @return Equals по полю {@link #id}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(id, user.id);
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
