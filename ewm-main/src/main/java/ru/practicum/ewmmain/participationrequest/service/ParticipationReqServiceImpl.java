package ru.practicum.ewmmain.participationrequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.participationrequest.model.ParticipationRequestDto;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipationReqServiceImpl implements ParticipationReqService {
    @Override
    public List<ParticipationRequestDto> getUserRequest(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmRequest(Integer userId, Integer eventId, Integer reqId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectRequest(Integer userId, Integer eventId, Integer reqId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Integer userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addRequest(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        return null;
    }
}
