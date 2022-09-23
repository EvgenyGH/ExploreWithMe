package ru.practicum.ewmmain.event.model;

import ru.practicum.ewmmain.user.model.UserDtoMapper;

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
}

