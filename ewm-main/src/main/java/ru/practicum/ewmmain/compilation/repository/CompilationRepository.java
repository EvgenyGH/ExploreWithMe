package ru.practicum.ewmmain.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmain.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Compilation c SET c.pinned = ?2 " +
            "WHERE c.id = ?1")
    int pinUnpinCompilation(Integer compilationId, Boolean pinned);
}
