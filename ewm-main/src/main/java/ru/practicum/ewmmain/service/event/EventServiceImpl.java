package ru.practicum.ewmmain.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.utils.client.StatisticsClient;
import ru.practicum.ewmmain.controller.event.SortOption;
import ru.practicum.ewmmain.exception.event.CategoryNotFoundException;
import ru.practicum.ewmmain.exception.event.EventNotFoundException;
import ru.practicum.ewmmain.utils.mapper.EventDtoMapper;
import ru.practicum.ewmmain.model.event.category.Category;
import ru.practicum.ewmmain.model.event.category.dto.CategoryDto;
import ru.practicum.ewmmain.utils.mapper.CategoryDtoMapper;
import ru.practicum.ewmmain.model.event.category.dto.CategoryNewDto;
import ru.practicum.ewmmain.model.event.dto.*;
import ru.practicum.ewmmain.model.event.location.Location;
import ru.practicum.ewmmain.utils.mapper.LocationDtoMapper;
import ru.practicum.ewmmain.repository.event.CategoryRepository;
import ru.practicum.ewmmain.repository.event.EventRepository;
import ru.practicum.ewmmain.repository.event.LocationRepository;
import ru.practicum.ewmmain.exception.OperationConditionViolationException;
import ru.practicum.ewmmain.model.event.*;
import ru.practicum.ewmmain.service.participationrequest.ParticipationReqService;
import ru.practicum.ewmmain.service.user.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    private final UserService userService;
    private final EventRepository repository;
    private final CategoryRepository catRepository;
    private final LocationRepository locRepository;
    private final Validator validator;
    private final StatisticsClient client;
    private final ParticipationReqService reqService;

    @Autowired
    public EventServiceImpl(UserService userService, EventRepository repository, CategoryRepository catRepository,
                            LocationRepository locRepository, StatisticsClient client,
                            @Lazy ParticipationReqService reqService) {
        this.userService = userService;
        this.repository = repository;
        this.catRepository = catRepository;
        this.locRepository = locRepository;
        this.client = client;
        this.reqService = reqService;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    //Получение событий с возможностью фильтрации.
    //Только опубликованные события.
    //Текстовый поиск по аннотации и описанию без учета регистра.
    //Если не указаны даты [rangeStart-rangeEnd] возвращаются события позже текущей даты и времени.
    //Информацию о запросе направляется в сервисе статистики.
    //Параметры:
    //text - текст для поиска в содержимом аннотации и подробном описании события;
    //categories - список идентификаторов категорий в которых будет вестись поиск;
    //paid - поиск только платных/бесплатных событий;
    //rangeStart - дата и время не раньше которых должно произойти событие
    //rangeEnd - дата и время не позже которых должно произойти событие
    //onlyAvailable - только события у которых не исчерпан лимит запросов на участие
    //sort - Вариант сортировки: по дате события или по количеству просмотров (EVENT_DATE, VIEWS)
    //from - количество событий, которые нужно пропустить для формирования текущего набора
    //size - количество событий в наборе
    @Override
    public List<EventDtoShort> getEvents(String text, List<Integer> categories, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable, SortOption sort, Integer from, Integer size,
                                         String ip, String uri) {

        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }

        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(1000);
        }

        //Сортировка по возрастанию даты
        List<Event> events = repository.getPublishedEvents(text != null ? text.toLowerCase() : null,
                categories, paid, rangeStart, rangeEnd, PageRequest.of(from / size, size, Direction.ASC,
                        "eventDate"));

        List<EventDtoShort> eventsDto = events.stream().map(event -> EventDtoMapper.toDtoShort(event,
                getConfRequests(event.getId()), getViews(event.getId()))).collect(Collectors.toList());

        //Только события у которых не исчерпан лимит запросов на участие
        if (onlyAvailable) {
            HashMap<Integer, Integer> limits = new HashMap<>();

            events.forEach(event -> limits.put(event.getId(), event.getParticipantLimit()));
            eventsDto = eventsDto.stream().filter(eventDto -> limits.get(eventDto.getId()).equals(0) ||
                    limits.get(eventDto.getId()) > eventDto.getConfirmedRequests()).collect(Collectors.toList());
        }

        //Сортировка событий по убыванию просмотров
        if (sort.equals(SortOption.VIEWS)) {
            eventsDto.sort(Comparator.comparing(EventDtoShort::getViews).reversed());
        }

        sendStatistics(ip, uri);

        log.trace("{} Found {} events", LocalDateTime.now(), eventsDto.size());

        return eventsDto;
    }

    @Override
    public EventDto getEventById(Integer eventId, String ip, String uri) {
        Event event = getEventById(eventId);

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new EventNotFoundException(String.format("Event id=%d is not published. Only published events allowed",
                    eventId));
        }

        sendStatistics(ip, uri);

        log.trace("{} Event id={} found : {}", LocalDateTime.now(), eventId, event);

        return EventDtoMapper.toDto(event, getConfRequests(eventId), getViews(eventId));
    }


    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        List<Category> categories = catRepository.findAll(
                PageRequest.of(from / size, size)).toList();

        log.trace("{} Found {} categories: {}", LocalDateTime.now(), categories.size(), categories);

        return categories.stream().map(CategoryDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        Category category = catRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category id=%d not found.", catId)));

        log.trace("{} Category id={} found: {}", LocalDateTime.now(), catId, category);

        return CategoryDtoMapper.toDto(category);
    }

    @Override
    public List<EventDtoShort> getUserEvents(Integer userId, Integer from, Integer size) {
        List<Event> events = repository.findAllByInitiatorId(userId,
                PageRequest.of(from / size, size));

        log.trace("{} Found {} events of user id={}", LocalDateTime.now(), events.size(), userId);

        return events.stream().map(event -> EventDtoMapper.toDtoShort(event,
                        getConfRequests(event.getId()), getViews(event.getId())))
                .collect(Collectors.toList());
    }


    //Изменить можно только отмененные события или события в состоянии ожидания модерации.
    //Если редактируется отменённое событие, то оно переходит в состояние ожидания модерации.
    @Override
    public EventDto updateEvent(Integer userId, EventUpdateDto eventUpdate) {
        Event event = checkConditions(userId, eventUpdate.getEventId());
        updateEventData(eventUpdate, event);

        validateEvent(event);
        repository.save(event);

        log.trace("{} Event id={} updated : {}", LocalDateTime.now(), event.getId(), event);

        return EventDtoMapper.toDto(event, getConfRequests(event.getId()), getViews(event.getId()));
    }

    @Override
    public EventDto addEvent(Integer userId, EventNewDto eventNew) {
        Location location = locRepository.getByLatAndLon(eventNew.getLocation().getLat(),
                        eventNew.getLocation().getLon())
                .orElseGet(() -> locRepository.save(LocationDtoMapper.toLocation(eventNew.getLocation())));

        Event event = repository.save(EventDtoMapper.toEvent(eventNew, userService.getUserById(userId),
                this.getCatById(eventNew.getCategory()), location));

        log.trace("{} New event id={} added : {}", LocalDateTime.now(), event.getId(), event);

        return EventDtoMapper.toDto(event, 0, 0);
    }

    @Override
    public EventDto getUserEvent(Integer userId, Integer eventId) {
        Event event = checkConditions(userId, eventId);

        log.trace("{} Event id={} user id={} found : {}", LocalDateTime.now(), eventId, userId, event);

        return EventDtoMapper.toDto(event, getConfRequests(eventId), getViews(eventId));
    }

    @Override
    public EventDto cancelEvent(Integer userId, Integer eventId) {
        Event event = checkConditions(userId, eventId);

        event.setState(State.CANCELED);
        repository.save(event);

        log.trace("{} Event id={} canceled : {}", LocalDateTime.now(), event.getId(), event);

        return EventDtoMapper.toDto(event, getConfRequests(eventId), getViews(eventId));
    }

    @Override
    public List<EventDto> getEventsAdmin(List<Integer> userIds, List<State> states,
                                         List<Integer> categoryIds, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Integer from, Integer size) {

        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }

        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(1000);
        }

        List<Event> events = repository.getEventsAdmin(userIds, states, categoryIds,
                rangeStart, rangeEnd, PageRequest.of(from / size, size));

        log.trace("{} Found {} events by admin", LocalDateTime.now(), events.size());

        return events.stream().map(event -> EventDtoMapper.toDto(event,
                        getConfRequests(event.getId()), getViews(event.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public EventDto updateEventAdmin(Integer eventId, EventUpdateAdminDto eventUpdate) {
        Event event = getEventById(eventId);

        updateEventDataAdm(eventUpdate, event);
        repository.save(event);

        log.trace("{} Event id={} updated by admin: {}", LocalDateTime.now(), event.getId(), event);

        return EventDtoMapper.toDto(event, getConfRequests(event.getId()), getViews(event.getId()));
    }

    //Дата начала события должна быть не ранее чем за час от даты публикации.
    //Событие должно быть в состоянии ожидания публикации.
    @Override
    public EventDto publishEvent(Integer eventId) {
        Event event = checkPublishConditions(eventId);

        event.setState(State.PUBLISHED);
        event.setPublished(LocalDateTime.now());
        repository.save(event);

        log.trace("{} Event id={} published : {}", LocalDateTime.now(), event.getId(), event);

        return EventDtoMapper.toDto(event, getConfRequests(eventId), getViews(eventId));
    }

    @Override
    public EventDto cancelEventAdmin(Integer eventId) {
        Event event = checkConditionsAdm(eventId);

        event.setState(State.CANCELED);
        repository.save(event);

        log.trace("{} Event id={} canceled : {}", LocalDateTime.now(), event.getId(), event);

        return EventDtoMapper.toDto(event, getConfRequests(eventId), getViews(eventId));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        catRepository.findById(categoryDto.getId());
        Category category = catRepository.save(CategoryDtoMapper.toCategory(categoryDto));

        log.trace("{} Category id={} updated: {}", LocalDateTime.now(), category.getId(), category);

        return CategoryDtoMapper.toDto(category);
    }

    @Override
    public CategoryDto addCategory(CategoryNewDto categoryNewDto) {
        Category category = catRepository.save(CategoryDtoMapper.toCategory(categoryNewDto));

        log.trace("{} New category added: {}", LocalDateTime.now(), category);

        return CategoryDtoMapper.toDto(category);
    }

    @Override
    public void deleteCategory(Integer catId) {
        catRepository.deleteById(catId);

        log.trace("{} Category id={} deleted", LocalDateTime.now(), catId);
    }

    @Override
    public Category getCatById(Integer catId) {
        Category category = catRepository.findById(catId).orElseThrow(() ->
                new CategoryNotFoundException(String.format("Category id=%d not found", catId)));

        log.trace("{} Found category id={} : {}", LocalDateTime.now(), catId, category);

        return category;
    }

    protected Integer getViews(Integer eventId) {
        return client.getViews(eventId);
    }

    protected Integer getConfRequests(Integer eventId) {
        return reqService.getConfRequests(eventId);
    }

    protected void validateEvent(Event event) {
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
    }

    protected void updateEventData(EventUpdateDto eventUpdate, Event event) {
        if (eventUpdate.getEventDate() != null) {
            event.setEventDate(eventUpdate.getEventDate());
        }

        if (eventUpdate.getAnnotation() != null) {
            event.setAnnotation(eventUpdate.getAnnotation());
        }

        if (eventUpdate.getCategory() != null) {
            event.setCategory(getCatById(eventUpdate.getCategory()));
        }

        if (eventUpdate.getPaid() != null) {
            event.setPaid(eventUpdate.getPaid());
        }

        if (eventUpdate.getDescription() != null) {
            event.setDescription(eventUpdate.getDescription());
        }

        if (eventUpdate.getTitle() != null) {
            event.setTitle(eventUpdate.getTitle());
        }

        if (eventUpdate.getParticipantLimit() != null) {
            event.setParticipantLimit(eventUpdate.getParticipantLimit());
        }

        if (event.getState().equals(State.CANCELED)) {
            event.setState(State.PENDING);
        }
    }

    protected Event checkConditions(Integer userId, Integer eventId) {
        Event event = checkConditionsAdm(eventId);

        if (!event.getInitiator().getId().equals(userId)) {
            throw new EventNotFoundException(String.format("Event id=%d is not created by user id=%d",
                    event.getId(), event.getInitiator().getId()));
        }

        return event;
    }

    protected Event checkConditionsAdm(Integer eventId) {
        Event event = getEventById(eventId);

        if (event.getState().equals(State.PUBLISHED)) {
            throw new OperationConditionViolationException("Published events can not be altered");
        }

        return event;
    }

    //Дата начала события должна быть не ранее чем за час от даты публикации.
    //Событие должно быть в состоянии ожидания публикации.
    protected Event checkPublishConditions(Integer eventId) {
        Event event = getEventById(eventId);

        if (!event.getState().equals(State.PENDING)) {
            throw new OperationConditionViolationException("Only PENDING events can be published");
        }

        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new ConstraintViolationException("Event has to start not earlier than 1 hour after publishing",
                    null);
        }

        return event;

    }

    @Override
    public Event getEventById(Integer eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format("Event id=%d not found", eventId)));

        log.trace("{} Found event id={} : {}", LocalDateTime.now(), eventId, event);

        return event;
    }

    protected void updateEventDataAdm(EventUpdateAdminDto eventUpdate, Event event) {
        if (eventUpdate.getEventDate() != null) {
            event.setEventDate(eventUpdate.getEventDate());
        }

        if (eventUpdate.getAnnotation() != null) {
            event.setAnnotation(eventUpdate.getAnnotation());
        }

        if (eventUpdate.getCategory() != null) {
            event.setCategory(getCatById(eventUpdate.getCategory()));
        }

        if (eventUpdate.getPaid() != null) {
            event.setPaid(eventUpdate.getPaid());
        }

        if (eventUpdate.getDescription() != null) {
            event.setDescription(eventUpdate.getDescription());
        }

        if (eventUpdate.getTitle() != null) {
            event.setTitle(eventUpdate.getTitle());
        }

        if (eventUpdate.getParticipantLimit() != null) {
            event.setParticipantLimit(eventUpdate.getParticipantLimit());
        }

        if (eventUpdate.getLocation() != null) {
            Location location = locRepository.getByLatAndLon(eventUpdate.getLocation().getLat(),
                            eventUpdate.getLocation().getLon())
                    .orElseGet(() -> locRepository.save(LocationDtoMapper.toLocation(eventUpdate.getLocation())));
            event.setLocation(location);
        }

        if (eventUpdate.getRequestModeration() != null) {
            event.setRequestModeration(eventUpdate.getRequestModeration());
        }
    }

    protected void sendStatistics(String ip, String uri) {
        client.sendStatistics(ip, uri);
    }
}