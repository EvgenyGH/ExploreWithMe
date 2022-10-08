package ru.practicum.ewmmain.controller.client.setlocation;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.controller.admin.setlocation.SetLocationControllerAdm;
import ru.practicum.ewmmain.controller.client.event.SortOption;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;
import ru.practicum.ewmmain.model.setlocation.SetLocation;
import ru.practicum.ewmmain.model.setlocation.dto.SetLocationDto;
import ru.practicum.ewmmain.service.setlocation.SetLocationService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Контроллер для работы с {@link SetLocation}.
 *
 * @author EvgenyS
 * @see SetLocationControllerAdm
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
@Validated
public class SetLocationController {
    /**
     * Сервис для работы с {@link SetLocation}
     */
    private final SetLocationService service;

    /**
     * Получить локацию по id.
     * @param locId id локации.
     * @return возвращает локацию по id.
     */
    @GetMapping("/{locId}")
    SetLocationDto getLocationById(@PathVariable @Min(1) Integer locId) {
        return service.getLocationById(locId);
    }

    /**
     * Получить все локации.
     * @param from начальная позиция в полной выборке.
     * @param size размер выборки.
     * @return возвращает все локации.
     */
    @GetMapping("/all")
    List<SetLocationDto> getAllLocations(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                                         @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size) {
        return service.getAllLocations(from, size);
    }

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
    @GetMapping("/{locId}/event")
    List<EventDtoShort> getEventsInLocation(@PathVariable @Min(1) Integer locId,
                                            @RequestParam(name = "sort", required = false, defaultValue = "EVENT_DATE")
                                            @NotNull SortOption sort,
                                            @RequestParam(required = false) State state,
                                            @RequestParam(required = false) String text,
                                            @RequestParam(required = false) List<Integer> categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            LocalDateTime rangeStart,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            LocalDateTime rangeEnd,
                                            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                                            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size) {
        return service.getEventsInLocation(locId, sort, state, text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, from, size);
    }
}
