package ru.practicum.ewmmain.participationrequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmmain.participationrequest.model.ParticipationRequest;

import java.util.Optional;

public interface ParticipationReqRepository extends JpaRepository<ParticipationRequest, Integer> {

    @Query(value = "SELECT p FROM ParticipationRequest p " +
            "WHERE p.requester.id = ?1 " +
            "AND p.event.id = ?2")
    Optional<ParticipationRequest> isUserRequsest(Integer userId, Integer eventId);

    @Query("SELECT count(p) FROM ParticipationRequest p " +
            "WHERE p.event.id = ?1 " +
            "AND p.status = 'CONFIRMED'")
    Integer getConfRequests(Integer eventId);


}
