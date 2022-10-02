package ru.practicum.ewmmain.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.model.event.category.dto.CategoryDto;
import ru.practicum.ewmmain.model.event.dto.EventDto;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;
import ru.practicum.ewmmain.model.event.dto.EventNewDto;
import ru.practicum.ewmmain.model.event.dto.EventUpdateDto;
import ru.practicum.ewmmain.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Закрытый контроллер событий({@link ru.practicum.ewmmain.model.event.Event})
 * для администратора.
 * @author Evgeny S
 * @see EventControllerAdm
 */
@RestController
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventService service;

    /**
     * Получение событий с возможностью фильтрации.
     * Только опубликованные события.
     * Текстовый поиск по аннотации и описанию без учета регистра.
     * Если не указаны даты [rangeStart-rangeEnd] возвращаются события позже текущей даты и времени.
     * Информация о запросе направляется в сервисе статистики.
     * @param text текст для поиска в содержимом аннотации и подробном описании события.
     * @param categories список идентификаторов категорий в которых будет вестись поиск.
     * @param paid поиск только платных/бесплатных событий.
     * @param rangeStart дата и время не раньше которых должно произойти событие.
     * @param rangeEnd дата и время не позже которых должно произойти событие.
     * @param onlyAvailable только события у которых не исчерпан лимит запросов на участие.
     * @param sort Вариант сортировки: по дате события или по количеству просмотров (EVENT_DATE, VIEWS).
     * @param from количество событий, которые нужно пропустить для формирования текущего набора.
     * @param size количество событий в наборе.
     * @param request информация о входящем запросе.
     * @return возвращает выборку событий {@link List}<{@link EventDtoShort}> согласно заданным фильтрам.
     */
    @GetMapping("/events")
    List<EventDtoShort> getEvents(@RequestParam(required = false) String text,
                                  @RequestParam(required = false) List<Integer> categories,
                                  @RequestParam(required = false) Boolean paid,
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

    /**
     * Получение подробной информации об опубликованном событии по его идентификатору.
     * Событие должно быть опубликовано.
     * Информацию о запросе направляется в сервисе статистики.
     * @param eventId id события.
     * @param request информация о входящем запросе.
     * @return возвращает событие {@link EventDto} по его id.
     */
    @GetMapping("/events/{id}")
    EventDto getEventById(@PathVariable(name = "id") @Min(0) Integer eventId, HttpServletRequest request) {
        return service.getEventById(eventId, request.getRemoteAddr(), request.getRequestURI());
    }

    /**
     * Получение категорий.
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора.
     * @param size количество категорий в наборе.
     * @return возвращает список категорий {@link List}<{@link CategoryDto}>.
     */
    @GetMapping("/categories")
    List<CategoryDto> getCategories(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                                    @RequestParam(required = false, defaultValue = "10") @Min(0) Integer size) {
        return service.getCategories(from, size);
    }

    /**
     * Получение информации о категории по её идентификатору.
     * @param catId id категории.
     * @return возвращает категорию {@link CategoryDto} по ее id.
     */
    @GetMapping("/categories/{catId}")
    CategoryDto getCategoryById(@PathVariable @Min(0) Integer catId) {
        return service.getCategoryById(catId);
    }

    /**
     * Получение событий, добавленных текущим пользователем.
     * @param userId id пользователя.
     * @param from количество событий, которые нужно пропустить для формирования текущего набора.
     * @param size количество событий в наборе.
     * @return возвращает список событий заданного пользователя {@link List}<{@link EventDtoShort}>.
     */
    @GetMapping("/users/{userId}/events")
    List<EventDtoShort> getUserEvents(@PathVariable @Min(0) Integer userId,
                                      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                                      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer size) {
        return service.getUserEvents(userId, from, size);
    }

    /**
     * Изменение события добавленного текущим пользователем.
     * Изменить можно только отмененные события или события в состоянии ожидания модерации.
     * Если редактируется отменённое событие, то оно переходит в состояние ожидания модерации.
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента.
     * @param eventUpdate данные для обновления события.
     * @param userId id пользователя.
     * @return возвращает обновленное событие {@link EventDto}.
     */
    @PatchMapping("/users/{userId}/events")
    EventDto updateEvent(@RequestBody @Valid EventUpdateDto eventUpdate, @PathVariable @Min(0) Integer userId) {
        return service.updateEvent(userId, eventUpdate);
    }

    /**
     * Добавление нового события.
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента.
     * @param eventNew данные для создания нового события.
     * @param userId id пользователя.
     * @return возвращает созданное событие {@link EventDto}.
     */
    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    EventDto addEvent(@RequestBody @Valid EventNewDto eventNew, @PathVariable @Min(0) Integer userId) {
        return service.addEvent(userId, eventNew);
    }

    /**
     * Получение полной информации о событии, добавленном текущим пользователем.
     * @param userId id пользователя.
     * @param eventId id события.
     * @return возвращает полную информацию о событии, добавленном текущим
     * пользователем {@link EventDto}.
     */
    @GetMapping("/users/{userId}/events/{eventId}")
    EventDto getUserEvent(@PathVariable @Min(0) Integer userId, @PathVariable @Min(0) Integer eventId) {
        return service.getUserEvent(userId, eventId);
    }

    /**
     * Отмена события, добавленного текущим пользователем.
     * Отменить можно только событие в состоянии ожидания модерации.
     * @param userId id пользователя.
     * @param eventId id события.
     * @return возвращает отмененное событие, добавленное текущим пользователем {@link EventDto}.
     */
    @PatchMapping("/users/{userId}/events/{eventId}")
    EventDto cancelEvent(@PathVariable @Min(0) Integer userId, @PathVariable @Min(0) Integer eventId) {
        return service.cancelEvent(userId, eventId);
    }
}
