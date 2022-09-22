package ru.practicum.ewmmain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
