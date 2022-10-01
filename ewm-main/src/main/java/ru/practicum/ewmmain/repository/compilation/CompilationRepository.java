package ru.practicum.ewmmain.repository.compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmain.model.compilation.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Compilation c SET c.pinned = ?2 " +
            "WHERE c.id = ?1")
    int pinUnpinCompilation(Integer compilationId, Boolean pinned);

    List<Compilation> findAllByPinned(boolean pinned, Pageable pageable);
}
