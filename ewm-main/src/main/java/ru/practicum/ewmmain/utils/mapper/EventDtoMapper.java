package ru.practicum.ewmmain.utils.mapper;

import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.event.category.Category;
import ru.practicum.ewmmain.model.event.dto.EventDto;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;
import ru.practicum.ewmmain.model.event.dto.EventNewDto;
import ru.practicum.ewmmain.model.event.location.Location;
import ru.practicum.ewmmain.model.user.User;

import java.time.LocalDateTime;

/**
 * Маппер класса {@link Event}
 * @author Evgeny S
 */
public class EventDtoMapper {
    /**
     * Маппер в класс {@link EventDto}
     * @param event экземпляр класса для конвертации.
     * @param confirmedRequests количество подтвержденных заявок на участие в событии.
     * @param views количество просмотров события.
     * @return возвращает конвертированный в класс {@link EventDto} экземпляр.
     */
    public static EventDto toDto(Event event, Integer confirmedRequests, Integer views) {
        return new EventDto(event.getAnnotation(),
                CategoryDtoMapper.toDto(event.getCategory()),
                confirmedRequests,
                event.getCreated(),
                event.getDescription(),
                event.getEventDate(),
                event.getId(),
                UserDtoMapper.toDtoShort(event.getInitiator()),
                LocationDtoMapper.toDto(event.getLocation()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublished(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                views);
    }

    /**
     * Маппер в класс {@link EventDtoShort}
     * @param event экземпляр класса для конвертации.
     * @param confirmedRequests количество подтвержденных заявок на участие в событии.
     * @param views количество просмотров события.
     * @return возвращает конвертированный в класс {@link EventDtoShort} экземпляр.
     */
    public static EventDtoShort toDtoShort(Event event, Integer confirmedRequests, Integer views) {
        return new EventDtoShort(event.getAnnotation(),
                CategoryDtoMapper.toDto(event.getCategory()),
                confirmedRequests,
                event.getEventDate(),
                event.getId(),
                UserDtoMapper.toDtoShort(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                views);
    }

    /**
     * Маппер в класс {@link Event}
     * @param eventNew экземпляр класса для конвертации.
     * @param initiator инициатор события.
     * @param category категория события.
     * @param location локация события.
     * @return возвращает конвертированный в класс {@link Event} экземпляр.
     */
    public static Event toEvent(EventNewDto eventNew, User initiator, Category category, Location location) {
        return new Event(null, eventNew.getAnnotation(), LocalDateTime.now(),
                eventNew.getDescription(), eventNew.getEventDate(), initiator,
                location, eventNew.getPaid(), eventNew.getParticipantLimit(), null,
                eventNew.getRequestModeration(), State.PENDING, eventNew.getTitle(), category);
    }
}