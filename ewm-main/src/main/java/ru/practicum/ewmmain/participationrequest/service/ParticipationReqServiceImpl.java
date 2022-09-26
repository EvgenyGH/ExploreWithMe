package ru.practicum.ewmmain.participationrequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.event.model.event.Event;
import ru.practicum.ewmmain.event.model.event.State;
import ru.practicum.ewmmain.event.service.EventService;
import ru.practicum.ewmmain.exception.OperationConditionViolationException;
import ru.practicum.ewmmain.participationrequest.exception.ParticipationRequestNotFoundException;
import ru.practicum.ewmmain.participationrequest.model.PartReqDtoMapper;
import ru.practicum.ewmmain.participationrequest.model.ParticipationRequest;
import ru.practicum.ewmmain.participationrequest.model.ParticipationRequestDto;
import ru.practicum.ewmmain.participationrequest.model.Status;
import ru.practicum.ewmmain.participationrequest.repository.ParticipationReqRepository;
import ru.practicum.ewmmain.user.model.User;
import ru.practicum.ewmmain.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipationReqServiceImpl implements ParticipationReqService {
    private final ParticipationReqRepository repository;
    private final EventService eventService;
    private final UserService userService;

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

    //Добавление запроса от текущего пользователя на участие в событии.
    //Нельзя добавить повторный запрос.
    //Инициатор события не может добавить запрос на участие в своём событии.
    //Нельзя участвовать в неопубликованном событии.
    //Если у события достигнут лимит запросов на участие - необходимо вернуть ошибку.
    //Если для события отключена пре-модерация запросов на участие, то запрос переходит в подтвержденный.
    @Override
    public ParticipationRequestDto addRequest(Integer userId, Integer eventId) {

        if (repository.getUserRequest(userId, eventId).isPresent()) {
            throw new OperationConditionViolationException(
                    String.format("Request from User id=%d to event id=%d already exists", userId, eventId));
        }

        Event event = eventService.getEventById(eventId);

        if (userId.equals(event.getInitiator().getId())) {
            throw new OperationConditionViolationException(
                    String.format("User id=%d is initiator of event id=%d", userId, eventId));
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new OperationConditionViolationException(
                    String.format("Event id=%d is not published yet", eventId));
        }

        if (event.getParticipantLimit().equals(0) ||
                event.getParticipantLimit() <= getConfRequests(eventId)) {
            throw new OperationConditionViolationException(
                    String.format("Event id=%d participants limit reached", eventId));
        }

        User user = userService.getUserById(userId);
        ParticipationRequest request = new ParticipationRequest(null, event, LocalDateTime.now(),
                user, null);

        if (event.getRequestModeration()) {
            request.setStatus(Status.PENDING);
        } else {
            request.setStatus(Status.CONFIRMED);
        }

        request = repository.save(request);

        log.trace("{} Participation request id={} added : {}", LocalDateTime.now(),
                request.getId(), request);

        return PartReqDtoMapper.toDto(request);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        ParticipationRequest request = repository.getUserRequestById(userId, requestId)
                .orElseThrow(() -> new ParticipationRequestNotFoundException(
                        String.format("User id=%d does not have request id=%d", userId, requestId)));
        repository.deleteById(requestId);

        log.trace("{} user id={} request id={} deleted : {}", LocalDateTime.now(), userId, requestId, request);

        return PartReqDtoMapper.toDto(request);
    }

    @Override
    public Integer getConfRequests(Integer eventId) {
        return repository.getConfRequests(eventId);
    }
}
