package ru.practicum.ewmmain.service.event;

import ru.practicum.ewmmain.controller.client.event.SortOption;
import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.event.category.Category;
import ru.practicum.ewmmain.model.event.category.dto.CategoryDto;
import ru.practicum.ewmmain.model.event.category.dto.CategoryNewDto;
import ru.practicum.ewmmain.model.event.dto.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса для работы с {@link Event}
 * @author Evgeny S
 * @see EventServiceImpl
 */
public interface EventService {
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
     * @param ip ip адрес пользователя.
     * @param uri uri ресурса.
     * @return возвращает выборку событий {@link List}<{@link EventDtoShort}> согласно заданным фильтрам.
     */
    List<EventDtoShort> getEvents(String text, List<Integer> categories, Boolean paid,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                  Boolean onlyAvailable, SortOption sort, Integer from,
                                  Integer size, String ip, String uri);

    /**
     * Получение подробной информации об опубликованном событии по его идентификатору.
     * Событие должно быть опубликовано.
     * Информацию о запросе направляется в сервисе статистики.
     * @param eventId id события.
     * @param ip ip адрес пользователя.
     * @param uri uri ресурса.
     * @return возвращает событие {@link EventDto} по его id.
     */
    EventDto getEventById(Integer eventId, String ip, String uri);

    /**
     * Получение категорий.
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора.
     * @param size количество категорий в наборе.
     * @return возвращает список категорий {@link List}<{@link CategoryDto}>.
     */
    List<CategoryDto> getCategories(Integer from, Integer size);

    /**
     * Получение информации о категории по её идентификатору.
     * @param catId id категории.
     * @return возвращает категорию {@link CategoryDto} по ее id.
     */
    CategoryDto getCategoryById(Integer catId);

    /**
     * Получение событий, добавленных текущим пользователем.
     * @param userId id пользователя.
     * @param from количество событий, которые нужно пропустить для формирования текущего набора.
     * @param size количество событий в наборе.
     * @return возвращает список событий заданного пользователя {@link List}<{@link EventDtoShort}>.
     */
    List<EventDtoShort> getUserEvents(Integer userId, Integer from, Integer size);

    /**
     * Изменение события добавленного текущим пользователем.
     * Изменить можно только отмененные события или события в состоянии ожидания модерации.
     * Если редактируется отменённое событие, то оно переходит в состояние ожидания модерации.
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента.
     * @param eventUpdate данные для обновления события.
     * @param userId id пользователя.
     * @return возвращает обновленное событие {@link EventDto}.
     */
    EventDto updateEvent(Integer userId, EventUpdateDto eventUpdate);

    /**
     * Добавление нового события.
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента.
     * @param eventNew данные для создания нового события.
     * @param userId id пользователя.
     * @return возвращает созданное событие {@link EventDto}.
     */
    EventDto addEvent(Integer userId, EventNewDto eventNew);

    /**
     * Получение полной информации о событии, добавленном текущим пользователем.
     * @param userId id пользователя.
     * @param eventId id события.
     * @return возвращает полную информацию о событии, добавленном текущим
     * пользователем {@link EventDto}.
     */
    EventDto getUserEvent(Integer userId, Integer eventId);

    /**
     * Отмена события, добавленного текущим пользователем.
     * Отменить можно только событие в состоянии ожидания модерации.
     * @param userId id пользователя.
     * @param eventId id события.
     * @return возвращает отмененное событие, добавленное текущим пользователем {@link EventDto}.
     */
    EventDto cancelEvent(Integer userId, Integer eventId);

    /**
     * Поиск событий по фильтрам.
     * @param userIds id пользователей.
     * @param states состояния событий.
     * @param categoryIds категории событий.
     * @param rangeStart нижняя граница диапазона времени начала событий.
     * @param rangeEnd верхняя граница диапазона времени начала событий.
     * @param from начальный элемент выборки.
     * @param size размер выборки.
     * @return возвращает выборку событий {@link List}<{@link EventDto}> согласно фильтрам.
     */
    List<EventDto> getEventsAdmin(List<Integer> userIds, List<State> states, List<Integer> categoryIds,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    /**
     * Редактирование события администратором. Валидация данных не требуется.
     * @param eventUpdate данные события для обновления.
     * @param eventId id обновляемого события.
     * @return возвращает обновленное событие {@link EventDto}.
     */
    EventDto updateEventAdmin(Integer eventId, EventUpdateAdminDto eventUpdate);

    /**
     * Публикация события.
     * Дата начала события должна быть не ранее чем за час от даты публикации.
     * Событие должно быть в состоянии ожидания публикации.
     * @param eventId id события.
     * @return возвращает опубликованное событие {@link EventDto}.
     */
    EventDto publishEvent(Integer eventId);

    /**
     * Отклонение события.
     * Событие не должно быть опубликовано.
     * @param eventId id события.
     * @return возвращает отклоненное событие {@link EventDto}.
     */
    EventDto cancelEventAdmin(Integer eventId);

    /**
     * Изменение категории.
     * @param categoryDto данные для обновления категории.
     * @return возвращает обновленную категорию  {@link CategoryDto}.
     */
    CategoryDto updateCategory(CategoryDto categoryDto);

    /**
     * Добавление новой категории.
     * @param categoryNewDto данные для создания новой категории.
     * @return возвращает созданную категорию {@link CategoryDto}.
     */
    CategoryDto addCategory(CategoryNewDto categoryNewDto);

    /**
     * Удаление категории.
     * С категорией не должно быть связано ни одного события.
     * @param catId Id категории для удаления.
     */
    void deleteCategory(Integer catId);

    /**
     * Получить категорию по id.
     * @param catId id категории.
     * @return возвращает категорию по ее id.
     */
    Category getCatById(Integer catId);

    /**
     * Получить событие по id.
     * @param eventId id события.
     * @return возвращает событие по его id.
     */
    Event getEventById(Integer eventId);
}
