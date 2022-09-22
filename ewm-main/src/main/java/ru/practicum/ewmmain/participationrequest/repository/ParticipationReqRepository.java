package ru.practicum.ewmmain.participationrequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.participationrequest.model.ParticipationRequest;

public interface ParticipationReqRepository extends JpaRepository<ParticipationRequest, Integer> {
}
