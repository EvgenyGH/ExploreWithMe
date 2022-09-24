package ru.practicum.ewmmain.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.event.model.*;
import ru.practicum.ewmmain.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventService service;

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
    @GetMapping("/events")
    List<EventDtoShort> getEvents(@RequestParam @NotNull String text,
                                  @RequestParam @NotNull List<Integer> categories,
                                  @RequestParam @NotNull Boolean paid,
                                  @RequestParam(required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  LocalDateTime rangeStart,
                                  @RequestParam(required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  LocalDateTime rangeEnd,
                                  @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                  @RequestParam(name = "sort") @NotNull SortOption sort,
                                  @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                                  @RequestParam(required = false, defaultValue = "10") @NotNull @Min(0) Integer size,
                                  HttpServletRequest request) {
        return service.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, request.getRemoteAddr(), request.getRequestURI());
    }

    //Получение подробной информации об опубликованном событии по его идентификатору.
    //Событие должно быть опубликовано.
    //Информацию о запросе направляется в сервисе статистики.
    @GetMapping("/events/{id}")
    EventDto getEventById(@PathVariable(name = "id") @Min(0) Integer eventId, HttpServletRequest request) {
        return service.getEventById(eventId, request.getRemoteAddr(), request.getRequestURI());
    }

    //Получение категорий.
    @GetMapping("/categories")
    List<CategoryDto> getCategories(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                                    @RequestParam(required = false, defaultValue = "10") @Min(0) Integer size) {
        return service.getCategories(from, size);
    }

    //Получение информации о категории по её идентификатору.
    @GetMapping("/categories/{catId}")
    CategoryDto getCategoryById(@PathVariable @Min(0) Integer catId) {
        return service.getCategoryById(catId);
    }

    //Получение событий, добавленных текущим пользователем.
    @GetMapping("/users/{userId}/events")
    List<EventDtoShort> getUserEvents(@PathVariable @Min(0) Integer userId,
                                      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                                      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer size) {
        return service.getUserEvents(userId, from, size);
    }

    //Изменение события добавленного текущим пользователем.
    //Изменить можно только отмененные события или события в состоянии ожидания модерации.
    //Если редактируется отменённое событие, то оно переходит в состояние ожидания модерации.
    //Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента.
    @PatchMapping("/users/{userId}/events")
    EventDto updateEvent(@RequestBody @Valid EventUpdateDto eventUpdate, @PathVariable @Min(0) Integer userId) {
        return service.updateEvent(userId, eventUpdate);
    }

    //Добавление нового события.
    //Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента.
    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    EventDto addEvent(@RequestBody @Valid EventNewDto eventNew, @PathVariable @Min(0) Integer userId) {
        return service.addEvent(userId, eventNew);
    }

    //Получение полной информации о событии добавленном текущим пользователем.
    @GetMapping("/users/{userId}/events/{eventId}")
    EventDto getUserEvent(@PathVariable @Min(0) Integer userId, @PathVariable @Min(0) Integer eventId) {
        return service.getUserEvent(userId, eventId);
    }

    //Отмена события добавленного текущим пользователем.
    //Отменить можно только событие в состоянии ожидания модерации.
    @PatchMapping("/users/{userId}/events/{eventId}")
    EventDto cancelEvent(@PathVariable @Min(0) Integer userId, @PathVariable @Min(0) Integer eventId) {
        return service.cancelEvent(userId, eventId);
    }

    //Поиск событий.
    @GetMapping("/admin/events")
    List<EventDto> getEventsAdmin(@RequestParam(name = "users") @Size(min = 1) List<Integer> userIds,
                                  @RequestParam @Size(min = 1) List<State> states,
                                  @RequestParam(name = "categories") @Size(min = 1) List<Integer> categoryIds,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  LocalDateTime rangeStart,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  LocalDateTime rangeEnd,
                                  @RequestParam(required = false, defaultValue = "0") Integer from,
                                  @RequestParam(required = false, defaultValue = "10") Integer size) {
        return service.getEventsAdmin(userIds, states, categoryIds, rangeStart, rangeEnd, from, size);
    }

    //Редактирование события.
    //Валидация данных не требуется.
    @PutMapping("/admin/events/{eventId}")
    EventDto updateEventAdmin(@RequestBody EventUpdateAdminDto eventUpdate, @PathVariable Integer eventId) {
        return service.updateEventAdmin(eventId, eventUpdate);
    }

    //Публикация события.
    //Дата начала события должна быть не ранее чем за час от даты публикации.
    //Событие должно быть в состоянии ожидания публикации.
    @PatchMapping("/admin/events/{eventId}/publish")
    EventDto publishEvent(@PathVariable @Min(0) Integer eventId) {
        return service.publishEvent(eventId);
    }

    //Отклонение события.
    //Событие не должно быть опубликовано.
    @PatchMapping("/admin/events/{eventId}/reject")
    EventDto cancelEventAdmin(@PathVariable @Min(0) Integer eventId) {
        return service.cancelEventAdmin(eventId);
    }

    //Изменение категории.
    @PatchMapping("/admin/categories")
    CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return service.updateCategory(categoryDto);
    }

    //Добавление новой категории.
    @PostMapping("/admin/categories")
    CategoryDto addCategory(@RequestBody @Valid CategoryNewDto categoryNewDto) {
        return service.addCategory(categoryNewDto);
    }

    //Удаление категории.
    //С категорией не должно быть связано ни одного события.
    @DeleteMapping("/admin/categories/{catId}")
    void deleteCategory(@PathVariable @Min(0) Integer catId) {
        service.deleteCategory(catId);
    }
}
