package ru.practicum.ewmmain.setlocation.service;

import ru.practicum.ewmmain.controller.client.event.SortOption;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;
import ru.practicum.ewmmain.setlocation.model.SetLocationDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для работы с {@link ru.practicum.ewmmain.setlocation.model.SetLocation}
 *
 * @author Evgeny S
 * @see SetLocationServiceImpl
 */
public interface SetLocationService {
    /**
     * Добавить новую локацию.
     * @param locationDto данные для добавления новой локации.
     * @return возвращает созданную локацию.
     */
    SetLocationDto addLocation(SetLocationDto locationDto);

    /**
     * Получить локацию по id.
     * @param locId id локации.
     * @return возвращает локацию по id.
     */
    SetLocationDto getLocationById(Integer locId);

    /**
     * Получить все локации.
     * @param from начальная позиция в полной выборке.
     * @param size размер выборки.
     * @return возвращает все локации.
     */
    List<SetLocationDto> getAllLocations(Integer from, Integer size);

    /**
     * Найти события в заданной локации с учетом фильтра.
     * @param locId id локации.
     * @param sort вариант сортировки: по дате события или по количеству просмотров (EVENT_DATE, VIEWS).
     *             По умолчанию EVENT_DATE.
     * @param state статус события: PENDING, PUBLISHED, CANCELED. Если null, то для любых статусов.
     * @param text текст для поиска в содержимом аннотации и описании события. Поиск без учета регистра.
     * @param categories список id категорий в которых будет вестись поиск. Если null, то по всем категориям.
     * @param paid поиск только платных/бесплатных событий. Если null, то поиск без фильтра по полю paid.
     * @param rangeStart дата и время не раньше которых должно произойти событие. Если не указана дата
     *                   rangeStart, то возвращаются события позже текущей даты и времени.
     * @param rangeEnd дата и время не позже которых должно произойти событие. Если не указана дата rangeEnd
     *                 возвращаются все события позже rangeStart.
     * @param onlyAvailable только события у которых не исчерпан лимит запросов на участие.
     * @param from количество событий, которые нужно пропустить для формирования текущего набора.
     * @param size количество событий в наборе.
     * @return возвращает список событий {@link List}<{@link EventDtoShort}> с учетом фильтров.
     */
    List<EventDtoShort> getEventsInLocation(Integer locId, SortOption sort, State state,
                                            String text, List<Integer> categories, Boolean paid,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            Boolean onlyAvailable, Integer from, Integer size);

    /**
     * Обновить локацию по id.
     * @param locationDto данные для обновления локации.
     * @param locId id локации.
     * @return возвращает локацию по id.
     */
    SetLocationDto updateDto(Integer locId, SetLocationDto locationDto);

    /**
     * Удалить локацию по id.
     * @param locId id локации.
     */
    void deleteLocation(Integer locId);
}
