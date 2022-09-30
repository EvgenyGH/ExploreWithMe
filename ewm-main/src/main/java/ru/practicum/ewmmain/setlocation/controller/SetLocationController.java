package ru.practicum.ewmmain.setlocation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.event.controller.SortOption;
import ru.practicum.ewmmain.event.model.event.EventDtoShort;
import ru.practicum.ewmmain.event.model.event.State;
import ru.practicum.ewmmain.setlocation.model.SetLocationDto;
import ru.practicum.ewmmain.setlocation.service.SetLocationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/location")
@Validated
public class SetLocationController {
    private final SetLocationService service;

    //Добавить новую локацию
    @PostMapping
    SetLocationDto addLocation(@RequestBody @Valid SetLocationDto locationDto) {
        return service.addLocation(locationDto);
    }

    //Получить локацию по ИД
    @GetMapping("/{locId}")
    SetLocationDto getLocationById(@PathVariable @Min(1) Integer locId) {
        return service.getLocationById(locId);
    }

    //Получить все локации
    @GetMapping("/all")
    List<SetLocationDto> getAllLocations(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                                         @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size) {
        return service.getAllLocations(from, size);
    }

    //Найти события в заданной локации
    //Событие:
    //Если не указана дата rangeStart возвращаются события позже текущей даты и времени;
    //Если не указана дата rangeEnd возвращаются все события позже rangeStart;
    //text - текст для поиска в содержимом аннотации и подробном описании события без учета регистра;
    //sort - Вариант сортировки: по дате события или по количеству просмотров (EVENT_DATE, VIEWS);
    //state - Статус события PENDING, PUBLISHED, CANCELED;
    //categories - список идентификаторов категорий в которых будет вестись поиск;
    //paid - поиск только платных/бесплатных событий;
    //rangeStart - дата и время не раньше которых должно произойти событие;
    //rangeEnd - дата и время не позже которых должно произойти событие;
    //onlyAvailable - только события у которых не исчерпан лимит запросов на участие;
    //from - количество событий, которые нужно пропустить для формирования текущего набора;
    //size - количество событий в наборе;
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

    //Обновить локацию по ИД
    @PatchMapping("/{locId}")
    SetLocationDto updateLocation(@RequestBody SetLocationDto locationDto,
                                  @PathVariable @Min(1) Integer locId) {
        return service.updateDto(locId, locationDto);
    }

    //Удалить локацию по ИД
    @DeleteMapping("/{locId}")
    void deleteLocation(@PathVariable @Min(1) Integer locId) {
        service.deleteLocation(locId);
    }


}
