package ru.practicum.ewmmain.mapper;

import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.event.category.Category;
import ru.practicum.ewmmain.model.event.dto.EventDto;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;
import ru.practicum.ewmmain.model.event.dto.EventNewDto;
import ru.practicum.ewmmain.model.event.location.Location;
import ru.practicum.ewmmain.model.user.User;

import java.time.LocalDateTime;

public class EventDtoMapper {
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

    public static Event toEvent(EventNewDto eventNew, User initiator, Category category, Location location) {
        return new Event(null, eventNew.getAnnotation(), LocalDateTime.now(),
                eventNew.getDescription(), eventNew.getEventDate(), initiator,
                location, eventNew.getPaid(), eventNew.getParticipantLimit(), null,
                eventNew.getRequestModeration(), State.PENDING, eventNew.getTitle(), category);
    }
}