package ru.practicum.ewmmain.service.participationrequest;

import ru.practicum.ewmmain.model.participationrequest.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationReqService {
    List<ParticipationRequestDto> getUserRequest(Integer userId, Integer eventId);

    ParticipationRequestDto confirmRequest(Integer userId, Integer eventId, Integer reqId);

    ParticipationRequestDto rejectRequest(Integer userId, Integer eventId, Integer reqId);

    List<ParticipationRequestDto> getUserRequests(Integer userId);

    ParticipationRequestDto addRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);

    Integer getConfRequests(Integer eventId);
}
