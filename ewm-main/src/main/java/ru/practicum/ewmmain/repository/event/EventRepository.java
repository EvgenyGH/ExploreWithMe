package ru.practicum.ewmmain.repository.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.setlocation.SetLocation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий класса {@link Event}
 *
 * @author Evgeny S
 */
public interface EventRepository extends JpaRepository<Event, Integer> {
    /**
     * Поиск события по id инициатора.
     *
     * @param initiatorId id инициатора события.
     * @param pageable    пагинация.
     * @return возвращает список событий, инициированных заданным пользователем {@link List}<{@link Event}>.
     */
    List<Event> findAllByInitiatorId(Integer initiatorId, Pageable pageable);

    /**
     * Получение подборки событий по фильтрам.
     *
     * @param userIds     id пользователей.
     * @param states      состояния.
     * @param categoryIds id категорий.
     * @param rangeStart  нижняя граница периода поиска.
     * @param rangeEnd    верхняя граница периода поиска.
     * @param pageable    пагинация.
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
     *
     * @param text       поиск текста по полям annotation и description класса {@link Event}.
     * @param categories id категорий.
     * @param paid       платные/бесплатные события.
     * @param rangeStart нижняя граница периода поиска.
     * @param rangeEnd   верхняя граница периода поиска.
     * @param pageable   пагинация.
     * @return возвращает подборку событий в соответствии с заданными фильтрами {@link List}<{@link Event}>.
     */
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

    /**
     * Поиск доступных событий в локации {@link SetLocation}.
     *
     * @param state      статус события: PENDING, PUBLISHED, CANCELED. Если null, то для любых статусов.
     * @param text       текст для поиска в содержимом аннотации и описании события. Поиск без учета регистра.
     * @param categories список id категорий в которых будет вестись поиск. Если null, то по всем категориям.
     * @param paid       поиск только платных/бесплатных событий. Если null, то поиск без фильтра по полю paid.
     * @param rangeStart дата и время не раньше которых должно произойти событие.
     * @param rangeEnd   дата и время не позже которых должно произойти событие.
     * @param latitude   широта.
     * @param longitude  долгота.
     * @param radius     радиус локации.
     * @param pageable   пагинация.
     * @return возвращает список событий {@link List}<{@link Event}> с учетом фильтров.
     */
    @Query(value = "SELECT e FROM Event e " +
            "WHERE (:text IS NULL OR lower(e.annotation) LIKE concat('%', :text, '%')) " +
            "AND (coalesce(:categories, null) IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd " +
            "AND (:state IS NULL OR e.state = :state) " +
            "AND function('distance', e.location.latitude, e.location.longitude, :lat, :long) <= :radius " +
            "AND (e.participantLimit = 0 " +
            "OR e.participantLimit > coalesce(" +
            "(SELECT count(p.id) FROM ParticipationRequest p " +
            "WHERE p.event.id = e.id " +
            "AND p.status = 'CONFIRMED' " +
            "GROUP BY p.event.id), 0)) " +
            "ORDER BY e.eventDate ASC")
    List<Event> getAvailEventsInLoc(@Param("state") State state, @Param("text") String text,
                                    @Param("categories") List<Integer> categories, @Param("paid") Boolean paid,
                                    @Param("rangeStart") LocalDateTime rangeStart,
                                    @Param("rangeEnd") LocalDateTime rangeEnd, @Param("lat") Float latitude,
                                    @Param("long") Float longitude, @Param("radius") Float radius,
                                    Pageable pageable);

    /**
     * Поиск событий в локации {@link SetLocation}.
     *
     * @param state      статус события: PENDING, PUBLISHED, CANCELED. Если null, то для любых статусов.
     * @param text       текст для поиска в содержимом аннотации и описании события. Поиск без учета регистра.
     * @param categories список id категорий в которых будет вестись поиск. Если null, то по всем категориям.
     * @param paid       поиск только платных/бесплатных событий. Если null, то поиск без фильтра по полю paid.
     * @param rangeStart дата и время не раньше которых должно произойти событие.
     * @param rangeEnd   дата и время не позже которых должно произойти событие.
     * @param latitude   широта.
     * @param longitude  долгота.
     * @param radius     радиус локации.
     * @param pageable   пагинация.
     * @return возвращает список событий {@link List}<{@link Event}> с учетом фильтров.
     */
    @Query(value = "SELECT e FROM Event e " +
            "WHERE (:text IS NULL OR lower(e.annotation) LIKE concat('%', :text, '%')) " +
            "AND (coalesce(:categories, null) IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd " +
            "AND (:state IS NULL OR e.state = :state) " +
            "AND function('distance', e.location.latitude, e.location.longitude, :lat, :long) <= :radius " +
            "ORDER BY e.eventDate ASC")
    List<Event> getEventsInLoc(@Param("state") State state, @Param("text") String text,
                               @Param("categories") List<Integer> categories, @Param("paid") Boolean paid,
                               @Param("rangeStart") LocalDateTime rangeStart,
                               @Param("rangeEnd") LocalDateTime rangeEnd, @Param("lat") Float latitude,
                               @Param("long") Float longitude, @Param("radius") Float radius,
                               Pageable pageable);
}
