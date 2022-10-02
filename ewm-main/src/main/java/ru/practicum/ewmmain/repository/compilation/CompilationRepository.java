package ru.practicum.ewmmain.repository.compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmain.model.compilation.Compilation;

import java.util.List;

/**
 * Репозиторий класса {@link Compilation}
 * @author Evgeny S
 */
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    /**
     * Закрепить/открепить подборку событий на главной странице.
     * @param compilationId id подборки.
     * @param pinned закрепить(true)/открепить(false).
     * @return возвращает количество обработанных строк.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE Compilation c SET c.pinned = ?2 " +
            "WHERE c.id = ?1")
    int pinUnpinCompilation(Integer compilationId, Boolean pinned);

    /**
     * Найти все закрепленные/незакрепленные подборки событий.
     * @param pinned закрепленные/незакрепленные подборки событий.
     * @param pageable пагинация.
     * @return возвращает список подборок в соответствии с фильтрами {@link List}<{@link Compilation}>.
     */
    List<Compilation> findAllByPinned(boolean pinned, Pageable pageable);
}
