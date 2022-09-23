package ru.practicum.ewmmain.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.event.controller.Sort;
import ru.practicum.ewmmain.event.model.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    @Override
    public List<EventDtoShort> getEvents(String text, List<Integer> categories, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable, Sort sort, Integer from, Integer size,
                                         String ip, String uri) {
        return null;
    }

    @Override
    public EventDto getEventById(Integer eventId, String ip, String uri) {
        return null;
    }

    @Override
    public CategoryDto getCategories(Integer from, Integer size) {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        return null;
    }

    @Override
    public List<EventDtoShort> getUserEvents(Integer userId, Integer from, Integer to) {
        return null;
    }

    @Override
    public EventDto updateEvent(Integer userId, EventUpdateDto eventUpdate) {
        return null;
    }

    @Override
    public EventDto addEvent(Integer userId, EventNewDto eventNew) {
        return null;
    }

    @Override
    public EventDto getUserEvent(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public EventDto cancelEvent(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public List<EventDto> getEventsAdmin(List<Integer> userIds, List<State> states,
                                         List<Integer> categoryIds, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventDto updateEventAdmin(Integer eventId, EventUpdateAdminDto eventUpdate) {
        return null;
    }

    @Override
    public EventDto publishEvent(Integer eventId) {
        return null;
    }

    @Override
    public EventDto cancelEventAdmin(Integer eventId) {
        return null;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto addCategory(CategoryNewDto categoryNewDto) {
        return null;
    }

    @Override
    public void deleteCategory(Integer catId) {

    }
}
