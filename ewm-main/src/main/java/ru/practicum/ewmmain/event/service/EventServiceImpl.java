package ru.practicum.ewmmain.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.event.controller.SortOption;
import ru.practicum.ewmmain.event.model.*;
import ru.practicum.ewmmain.event.repository.CategoryRepository;
import ru.practicum.ewmmain.event.repository.EventRepository;
import ru.practicum.ewmmain.event.repository.LocationRepository;
import ru.practicum.ewmmain.exception.CategoryNotFoundException;
import ru.practicum.ewmmain.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserService userService;
    private final EventRepository repository;
    private final CategoryRepository catRepository;
    private final LocationRepository locRepository;

    @Override
    public List<EventDtoShort> getEvents(String text, List<Integer> categories, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable, SortOption sort, Integer from, Integer size,
                                         String ip, String uri) {
        return null;
    }

    @Override
    public EventDto getEventById(Integer eventId, String ip, String uri) {
        return null;
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        List<Category> categories = catRepository.findAll(
                PageRequest.of(from / size, size, Direction.ASC, "name")).toList();

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
    public List<EventDtoShort> getUserEvents(Integer userId, Integer from, Integer to) {
        return null;
    }

    @Override
    public EventDto updateEvent(Integer userId, EventUpdateDto eventUpdate) {
        return null;
    }

    @Override
    public EventDto addEvent(Integer userId, EventNewDto eventNew) {
        //TODO: 24.09.2022
        Location location = locRepository.getByLatAndLon(eventNew.getLocation().getLat(),
                        eventNew.getLocation().getLon())
                .orElse(locRepository.save(LocationDtoMapper.toLocation(eventNew.getLocation())));

        Event event = repository.save(EventDtoMapper.toEvent(eventNew, userService.getUserById(userId),
                this.getCatById(eventNew.getCategory()), location));

        return EventDtoMapper.toDto(event, null, null);
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
}