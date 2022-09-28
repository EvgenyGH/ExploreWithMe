package ru.practicum.ewmmain.event.model.event;

import ru.practicum.ewmmain.event.model.category.Category;
import ru.practicum.ewmmain.event.model.category.CategoryDtoMapper;
import ru.practicum.ewmmain.event.model.location.Location;
import ru.practicum.ewmmain.event.model.location.LocationDtoMapper;
import ru.practicum.ewmmain.user.model.User;
import ru.practicum.ewmmain.user.model.UserDtoMapper;

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