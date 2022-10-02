package ru.practicum.ewmmain.setlocation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.controller.client.event.SortOption;
import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;
import ru.practicum.ewmmain.repository.event.EventRepository;
import ru.practicum.ewmmain.service.participationrequest.ParticipationReqService;
import ru.practicum.ewmmain.setlocation.exception.SetLocationNotFoundException;
import ru.practicum.ewmmain.setlocation.model.SetLocDtoMapper;
import ru.practicum.ewmmain.setlocation.model.SetLocation;
import ru.practicum.ewmmain.setlocation.model.SetLocationDto;
import ru.practicum.ewmmain.setlocation.repository.SetLocationRepository;
import ru.practicum.ewmmain.utils.client.StatisticsClient;
import ru.practicum.ewmmain.utils.mapper.EventDtoMapper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Релизация интерфейса {@link SetLocationService}
 *
 * @author Evgeny S
 * @see SetLocationService
 */
@Slf4j
@Service
public class SetLocationServiceImpl implements SetLocationService {
    /**
     * Репозиторий для работы с сущностью {@link SetLocation}
     */
    private final SetLocationRepository repository;
    /**
     * Репозиторий для работы с сущностью {@link Event}
     */
    private final EventRepository eventRepository;
    /**
     * Репозиторий для работы с сущностью {@link ru.practicum.ewmmain.model.participationrequest.ParticipationRequest}
     */
    private final ParticipationReqService reqService;
    /**
     * Клиент сервиса статистики.
     */
    private final StatisticsClient client;
    /**
     * Стандартный валидатор.
     */
    private final Validator validator;

    /**
     * Конструктор класса {@link SetLocationServiceImpl}
     * @param repository Репозиторий для работы с сущностью {@link SetLocation}
     * @param eventRepository Репозиторий для работы с сущностью {@link Event}
     * @param reqService Репозиторий для работы с сущностью
     * {@link ru.practicum.ewmmain.model.participationrequest.ParticipationRequest}
     * @param client Клиент сервиса статистики.
     */
    public SetLocationServiceImpl(SetLocationRepository repository, EventRepository eventRepository,
                                  ParticipationReqService reqService, StatisticsClient client) {
        this.repository = repository;
        this.eventRepository = eventRepository;
        this.reqService = reqService;
        this.client = client;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public SetLocationDto addLocation(SetLocationDto locationDto) {
        SetLocation location = SetLocDtoMapper.toSetLocation(locationDto);

        location.setId(null);

        location = repository.save(location);

        log.trace("{} Set location id={} added : {}", LocalDateTime.now(), location.getId(), location);

        return SetLocDtoMapper.toDto(location);
    }

    @Override
    public SetLocationDto getLocationById(Integer locId) {
        SetLocation location = repository.findById(locId).orElseThrow(() ->
                new SetLocationNotFoundException(String.format("Set location id=%d not found", locId)));

        log.trace("{} Set location id={} found : {}", LocalDateTime.now(), locId, location);

        return SetLocDtoMapper.toDto(location);
    }

    @Override
    public List<SetLocationDto> getAllLocations(Integer from, Integer size) {
        List<SetLocation> locations = repository.findAll(PageRequest.of(from / size, size)).getContent();

        log.trace("{} Found {} set locations", LocalDateTime.now(), locations.size());

        return locations.stream().map(SetLocDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDtoShort> getEventsInLocation(Integer locId, SortOption sort, State state,
                                                   String text, List<Integer> categories, Boolean paid,
                                                   LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                   Boolean onlyAvailable, Integer from, Integer size) {

        SetLocation location = repository.findById(locId).orElseThrow(
                ()-> new SetLocationNotFoundException(String.format("Set location id=%d not found", locId)));

        if (text == null) {
            text = "";
        }

        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }

        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(1000);
        }

        List<Event> events;

        //Только события у которых не исчерпан лимит запросов на участие
        if (onlyAvailable){
            events = eventRepository.getAvailEventsInLoc(state, text.toLowerCase(), categories,
                    paid, rangeStart, rangeEnd, location.getLatitude(), location.getLongitude(),
                    location.getRadius(), PageRequest.of(from / size, size));
        } else {
            events = eventRepository.getEventsInLoc(state, text.toLowerCase(), categories,
                    paid, rangeStart, rangeEnd, location.getLatitude(), location.getLongitude(),
                    location.getRadius(), PageRequest.of(from / size, size));
        }

        List<EventDtoShort> eventsDto = events.stream().map(event -> EventDtoMapper.toDtoShort(event,
                reqService.getConfRequests(event.getId()), client.getViews(event.getId())))
                .collect(Collectors.toList());

        //Сортировка событий по убыванию просмотров
        if (sort.equals(SortOption.VIEWS)) {
            eventsDto.sort(Comparator.comparing(EventDtoShort::getViews).reversed());
        }

        log.trace("{} Found {} events. locId={}, sort={}, text={}, categories={}, paid={}," +
                "rangeStart={}, rangeEnd={}, onlyAvailable={}, from={}, size={}", LocalDateTime.now(),
                eventsDto.size(), locId, sort.name(), text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, from, size);

        return eventsDto;
    }

    @Override
    public SetLocationDto updateDto(Integer locId, SetLocationDto locationDto) {
        SetLocation location = repository.findById(locId).orElseThrow(() ->
                new SetLocationNotFoundException(String.format("Set location id=%d not found", locId)));

        updateLocation(location, locationDto);

        Set<ConstraintViolation<SetLocation>> violations = validator.validate(location);

        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }

        repository.save(location);

        log.trace("{} Set location id={} updated : {}", LocalDateTime.now(), locId, location);

        return SetLocDtoMapper.toDto(location);
    }

    /**
     * Обновить данные локации.
     * @param location локация для обновления.
     * @param locationDto новые данные.
     */
    private void updateLocation(SetLocation location, SetLocationDto locationDto) {
        if (locationDto.getDescription() != null) {
            location.setDescription(locationDto.getDescription());
        }

        if (locationDto.getName() != null) {
            location.setName(locationDto.getName());
        }

        if (locationDto.getLongitude() != null) {
            location.setLongitude(locationDto.getLongitude());
        }

        if (locationDto.getLatitude() != null) {
            location.setLatitude(locationDto.getLatitude());
        }

        if (locationDto.getRadius() != null) {
            location.setRadius(locationDto.getRadius());
        }
    }

    @Override
    public void deleteLocation(Integer locId) {
        repository.deleteById(locId);
        log.trace("{} set location id={} deleted", LocalDateTime.now(), locId);
    }
}
