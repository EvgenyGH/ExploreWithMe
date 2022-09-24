package ru.practicum.ewmmain.event.service;

import ru.practicum.ewmmain.event.controller.SortOption;
import ru.practicum.ewmmain.event.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventDtoShort> getEvents(String text, List<Integer> categories, Boolean paid,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                  Boolean onlyAvailable, SortOption sort, Integer from,
                                  Integer size, String ip, String uri);

    EventDto getEventById(Integer eventId, String ip, String uri);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Integer catId);

    List<EventDtoShort> getUserEvents(Integer userId, Integer from, Integer size);

    EventDto updateEvent(Integer userId, EventUpdateDto eventUpdate);

    EventDto addEvent(Integer userId, EventNewDto eventNew);

    EventDto getUserEvent(Integer userId, Integer eventId);

    EventDto cancelEvent(Integer userId, Integer eventId);

    List<EventDto> getEventsAdmin(List<Integer> userIds, List<State> states, List<Integer> categoryIds,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventDto updateEventAdmin(Integer eventId, EventUpdateAdminDto eventUpdate);

    EventDto publishEvent(Integer eventId);

    EventDto cancelEventAdmin(Integer eventId);

    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto addCategory(CategoryNewDto categoryNewDto);

    void deleteCategory(Integer catId);

    Category getCatById(Integer catId);
}
