package ru.practicum.ewmmain.event.model;

import ru.practicum.ewmmain.compilation.model.Compilation;
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
                UserDtoMapper.toDtoShort(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublished(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                views);
    }

    public static EventDtoShort toDtoShort(Event event, Integer confirmedRequests, Integer views) {
        return new EventDtoShort(event.getId(),
                event.getAnnotation(),
                CategoryDtoMapper.toDto(event.getCategory()),
                confirmedRequests,
                event.getEventDate(),
                UserDtoMapper.toDtoShort(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                views);
    }

    public static Event toEvent(User initiator, EventNewDto eventNew, LocalDateTime published,
                                State state, Compilation compilation, Category category) {
        return new Event(null, eventNew.getAnnotation(), LocalDateTime.now(),
                eventNew.getDescription(),eventNew.getEventDate(), initiator,
                eventNew.getLocation(), eventNew.getPaid(), eventNew.getParticipantLimit(),
                published, eventNew.getRequestModeration(), state, eventNew.getTitle(), compilation, category);
    }

    public static Event toEvent(EventNewDto eventNew, User initiator, Category category) {
        return EventDtoMapper.toEvent(initiator, eventNew, null, State.PENDING,
                null, category);
    }
}