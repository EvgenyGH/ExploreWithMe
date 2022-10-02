package ru.practicum.ewmmain.setlocation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.setlocation.model.SetLocationDto;
import ru.practicum.ewmmain.setlocation.service.SetLocationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Контроллер для работы с {@link ru.practicum.ewmmain.setlocation.model.SetLocation}.
 * Только для администратора.
 *
 * @author EvgenyS
 * @see SetLocationController
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/location")
@Validated
public class SetLocationControllerAdm {
    /**
     * Сервис для работы с {@link ru.practicum.ewmmain.setlocation.model.SetLocation}
     */
    private final SetLocationService service;

    /**
     * Добавить новую локацию.
     * @param locationDto данные для добавления новой локации.
     * @return возвращает созданную локацию.
     */
    @PostMapping
    SetLocationDto addLocation(@RequestBody @Valid SetLocationDto locationDto) {
        return service.addLocation(locationDto);
    }

    /**
     * Обновить локацию по id.
     * @param locationDto данные для обновления локации.
     * @param locId id локации.
     * @return возвращает локацию по id.
     */
    @PatchMapping("/{locId}")
    SetLocationDto updateLocation(@RequestBody SetLocationDto locationDto,
                                  @PathVariable @Min(1) Integer locId) {
        return service.updateDto(locId, locationDto);
    }

    /**
     * Удалить локацию по id.
     * @param locId id локации.
     */
    @DeleteMapping("/{locId}")
    void deleteLocation(@PathVariable @Min(1) Integer locId) {
        service.deleteLocation(locId);
    }
}
