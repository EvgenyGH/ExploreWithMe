package ru.practicum.ewmmain.service.participationrequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.exception.OperationConditionViolationException;
import ru.practicum.ewmmain.exception.participationrequest.ParticipationRequestNotFoundException;
import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.participationrequest.ParticipationRequest;
import ru.practicum.ewmmain.model.participationrequest.Status;
import ru.practicum.ewmmain.model.participationrequest.dto.ParticipationRequestDto;
import ru.practicum.ewmmain.model.user.User;
import ru.practicum.ewmmain.repository.participationrequest.ParticipationReqRepository;
import ru.practicum.ewmmain.service.event.EventService;
import ru.practicum.ewmmain.service.user.UserService;
import ru.practicum.ewmmain.utils.mapper.PartReqDtoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация Интерфейса {@link ParticipationReqService}
 * @author Evgeny S
 * @see ParticipationReqService
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipationReqServiceImpl implements ParticipationReqService {
    /**
     * Репозиторий сущности {@link ParticipationRequest}
     */
    private final ParticipationReqRepository repository;
    /**
     * Сервис для работы с {@link Event}.
     */
    private final EventService eventService;
    /**
     * Сервис для работы с {@link User}.
     */
    private final UserService userService;

    @Override
    public List<ParticipationRequestDto> getUserRequest(Integer userId, Integer eventId) {
        List<ParticipationRequest> requests = repository.getUserEventRequests(userId, eventId);

        log.trace("{} Found {} requests (initiator id={}, request id={}", LocalDateTime.now(),
                requests.size(), userId, eventId);

        return requests.stream().map(PartReqDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(Integer userId, Integer eventId, Integer reqId) {
        ParticipationRequest request = repository.getUserRequest(userId, eventId, reqId)
                .orElseThrow(() -> new ParticipationRequestNotFoundException(String.format(
                        "Request id=%d for event id=%d created by user id=%d not found",
                        reqId, eventId, userId)));
        Event event = request.getEvent();

        if (event.getParticipantLimit().equals(0) || !event.getRequestModeration()) {
            throw new OperationConditionViolationException(
                    "Confirmation of events without participation limits or pre-moderation not required");
        }

        if (event.getParticipantLimit() <= getConfRequests(eventId)) {
            throw new OperationConditionViolationException(
                    String.format("Event id=%d participants limit reached", eventId));
        }

        repository.confirmRequest(reqId);
        request.setStatus(Status.CONFIRMED);

        log.trace("{} Event id={} confirmed", LocalDateTime.now(), eventId);

        if (event.getParticipantLimit().equals(getConfRequests(eventId))) {
            repository.rejectNotConfirmed(eventId);

            log.trace("{} Participation limit reached. Not confirmed requests for event id={} rejected",
                    LocalDateTime.now(), eventId);
        }

        return PartReqDtoMapper.toDto(request);
    }

    @Override
    public ParticipationRequestDto rejectRequest(Integer userId, Integer eventId, Integer reqId) {
        ParticipationRequest request = repository.getUserRequest(userId, eventId, reqId)
                .orElseThrow(() -> new ParticipationRequestNotFoundException(String.format(
                        "Request id=%d for event id=%d created by user id=%d not found",
                        reqId, eventId, userId)));

        request.setStatus(Status.REJECTED);
        repository.save(request);

        log.trace("{} Request id={} rejected", LocalDateTime.now(), reqId);

        return PartReqDtoMapper.toDto(request);
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Integer userId) {
        List<ParticipationRequest> requests = repository.findAllByRequesterId(userId);

        log.trace("{} Found {} requests (requester id={}))", LocalDateTime.now(),
                requests.size(), userId);

        return requests.stream().map(PartReqDtoMapper::toDto).collect(Collectors.toList());
    }

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

        if (!event.getParticipantLimit().equals(0) &&
                event.getParticipantLimit() <= getConfRequests(eventId)) {
            throw new OperationConditionViolationException(
                    String.format("Event id=%d participants limit reached", eventId));
        }

        User user = userService.getUserById(userId);
        ParticipationRequest request = new ParticipationRequest(null, event, LocalDateTime.now(),
                user, null);

        if (!event.getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
        } else {
            request.setStatus(Status.PENDING);
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

        request.setStatus(Status.CANCELED);
        repository.save(request);

        log.trace("{} user id={} request id={} canceled : {}", LocalDateTime.now(), userId, requestId, request);

        return PartReqDtoMapper.toDto(request);
    }

    @Override
    public Integer getConfRequests(Integer eventId) {
        Integer confRequests = repository.getConfRequests(eventId);

        log.trace("{} Found {} confirmed requests of event id={}", LocalDateTime.now(),
                confRequests, eventId);

        return confRequests;
    }
}
