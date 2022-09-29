package ru.practicum.ewmmain.setlocation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.event.model.event.EventDtoShort;
import ru.practicum.ewmmain.setlocation.model.SetLocationDto;
import ru.practicum.ewmmain.setlocation.service.SetLocationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    List<SetLocationDto> getAllLocations() {
        return service.getAllLocations();
    }

    //Найти события в заданной локации
    @GetMapping("/{locId}/event/{eventId}")
    List<EventDtoShort> getEventsInLocation(@PathVariable @Min(1) Integer locId,
                                            @PathVariable @Min(1) Integer eventId) {
        return service.getEventsInLocation(locId, eventId);
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
