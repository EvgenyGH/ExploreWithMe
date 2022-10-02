package ru.practicum.ewmmain.repository.participationrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmain.model.participationrequest.ParticipationRequest;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий класса {@link ParticipationRequest}
 * @author Evgeny S
 */
public interface ParticipationReqRepository extends JpaRepository<ParticipationRequest, Integer> {

    /**
     * Поиск заявки по id автора заявки и id события.
     * @param requesterId id автор заявки.
     * @param eventId id события.
     * @return возвращает заявку по id автора запроса и id события
     * {@link Optional}<{@link ParticipationRequest}>.
     */
    @Query(value = "SELECT p FROM ParticipationRequest p " +
            "WHERE p.requester.id = ?1 " +
            "AND p.event.id = ?2")
    Optional<ParticipationRequest> getUserRequest(Integer requesterId, Integer eventId);

    /**
     * Поиск заявки по id инициатора события и id события и id заявки.
     * @param initiatorId id инициатора события.
     * @param eventId id события.
     * @param requestId id заявки.
     * @return возвращает заявку по id инициатора события и id события и id заявки
     * {@link Optional}<{@link ParticipationRequest}>.
     */
    @Query(value = "SELECT p FROM ParticipationRequest p " +
            "WHERE p.event.initiator.id = ?1 " +
            "AND p.event.id = ?2 " +
            "AND p.id = ?3")
    Optional<ParticipationRequest> getUserRequest(Integer initiatorId, Integer eventId, Integer requestId);

    /**Поиск заявки по id автора заявки и id заявки.
     * @param requesterId id автор заявки.
     * @param requestId id заявки.
     * @return возвращает заявку по id автора заявки и id заявки
     * {@link Optional}<{@link ParticipationRequest}>.
     */
    @Query(value = "SELECT p FROM ParticipationRequest p " +
            "WHERE p.requester.id = ?1 " +
            "AND p.id = ?2")
    Optional<ParticipationRequest> getUserRequestById(Integer requesterId, Integer requestId);

    /**
     * Получение количества подтвержденных заявок на событие.
     * @param eventId id события.
     * @return возвращает количество подтвержденных заявок на событие.
     */
    @Query("SELECT count(p) FROM ParticipationRequest p " +
            "WHERE p.event.id = ?1 " +
            "AND p.status = 'CONFIRMED'")
    Integer getConfRequests(Integer eventId);

    /**
     * Подтвердить заявку.
     * @param reqId id заявки.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE ParticipationRequest p SET p.status = 'CONFIRMED' " +
            "WHERE p.id = ?1")
    void confirmRequest(Integer reqId);

    /**
     * Отклонить заявку.
     * @param eventId id заявки.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE ParticipationRequest p SET p.status = 'REJECTED' " +
            "WHERE p.status <> 'CONFIRMED' " +
            "AND p.event.id = ?1")
    void rejectNotConfirmed(Integer eventId);

    /**
     * Получить все заявки пользователя.
     * @param requesterId id автора заявок.
     * @return возвращает все заявки пользователя {@link List}<{@link ParticipationRequest}>.
     */
    List<ParticipationRequest> findAllByRequesterId(Integer requesterId);

    /**
     * Получить заявки по id инициатора события и id события.
     * @param initiatorId id инициатора события.
     * @param eventId id события.
     * @return возвращает все заявки по id инициатора события и id события
     * {@link List}<{@link ParticipationRequest}>.
     */
    @Query(value = "SELECT p FROM ParticipationRequest p " +
            "WHERE p.event.initiator.id = ?1 " +
            "AND p.event.id = ?2")
    List<ParticipationRequest> getUserEventRequests(Integer initiatorId, Integer eventId);
}
