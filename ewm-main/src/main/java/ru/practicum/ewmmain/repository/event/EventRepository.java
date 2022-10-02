package ru.practicum.ewmmain.repository.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.event.State;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий класса {@link Event}
 * @author Evgeny S
 */
public interface EventRepository extends JpaRepository<Event, Integer> {
    /**
     * Поиск события по id инициатора.
     * @param initiatorId id инициатора события.
     * @param pageable пагинация.
     * @return возвращает список событий, инициированных заданным пользователем {@link List}<{@link Event}>.
     */
    List<Event> findAllByInitiatorId(Integer initiatorId, Pageable pageable);

    /**
     * Получение подборки событий по фильтрам.
     * @param userIds id пользователей.
     * @param states состояния.
     * @param categoryIds id категорий.
     * @param rangeStart нижняя граница периода поиска.
     * @param rangeEnd верхняя граница периода поиска.
     * @param pageable пагинация.
     * @return возвращает подборку событий в соответствии с заданными фильтрами {@link List}<{@link Event}>.
     */
    @Query(value = "SELECT e FROM Event e " +
            "WHERE (coalesce(:userIds, null) IS NULL OR e.initiator.id IN :userIds) " +
            "AND (coalesce(:states, null) IS NULL OR e.state IN :states) " +
            "AND (coalesce(:categoryIds, null) IS NULL OR e.category.id IN :categoryIds) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd")
    List<Event> getEventsAdmin(@Param("userIds") List<Integer> userIds, @Param("states") List<State> states,
                               @Param("categoryIds") List<Integer> categoryIds,
                               @Param("rangeStart") LocalDateTime rangeStart,
                               @Param("rangeEnd") LocalDateTime rangeEnd, Pageable pageable);


    /**
     * Поиск только опубликованных событий с учетом фильтров.
     * @param text поиск текста по полям annotation и description класса {@link Event}.
     * @param categories id категорий.
     * @param paid платные/бесплатные события.
     * @param rangeStart нижняя граница периода поиска.
     * @param rangeEnd верхняя граница периода поиска.
     * @param pageable пагинация.
     * @return возвращает подборку событий в соответствии с заданными фильтрами {@link List}<{@link Event}>.
     */
    //Только опубликованные события.
    @Query(value = "SELECT e FROM Event e " +
            "WHERE ((:text IS NULL OR lower(e.annotation) LIKE concat('%', :text, '%')) " +
            "OR (:text IS NULL OR lower(e.description) LIKE concat('%', :text, '%'))) " +
            "AND (coalesce(:categories, null) IS NULL OR e.category.id IN :categories) " +
            "AND (coalesce(:paid, null) IS NULL OR e.paid = :paid) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd " +
            "AND e.state = 'PUBLISHED'")
    List<Event> getPublishedEvents(@Param("text") String text, @Param("categories") List<Integer> categories,
                                   @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                                   @Param("rangeEnd") LocalDateTime rangeEnd, Pageable pageable);
}
