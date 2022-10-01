package ru.practicum.ewmmain.controller.admin.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.model.event.category.dto.CategoryDto;
import ru.practicum.ewmmain.model.event.category.dto.CategoryNewDto;
import ru.practicum.ewmmain.model.event.dto.EventDto;
import ru.practicum.ewmmain.model.event.dto.EventUpdateAdminDto;
import ru.practicum.ewmmain.service.event.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class EventControllerAdm {
    private final EventService service;

    //Поиск событий.
    @GetMapping("/admin/events")
    List<EventDto> getEventsAdmin(@RequestParam(name = "users", required = false) List<Integer> userIds,
                                  @RequestParam(required = false) List<State> states,
                                  @RequestParam(required = false, name = "categories") List<Integer> categoryIds,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  LocalDateTime rangeStart,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
