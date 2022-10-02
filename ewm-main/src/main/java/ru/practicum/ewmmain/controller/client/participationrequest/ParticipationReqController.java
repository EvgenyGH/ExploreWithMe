package ru.practicum.ewmmain.controller.client.participationrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.model.participationrequest.dto.ParticipationRequestDto;
import ru.practicum.ewmmain.service.participationrequest.ParticipationReqService;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Публичный контроллер заявок на участие в событии
 * ({@link ru.practicum.ewmmain.model.participationrequest.ParticipationRequest})
 * @author Evgeny S
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class ParticipationReqController {
    /**
     * Сервис для работы с заявками на участие в событии.
     */
    private final ParticipationReqService service;

    /**
     * Получение информации о запросах на участие в событии текущего пользователя.
     * @param userId id пользователя.
     * @param eventId id события.
     * @return возвращает список запросов пользователя на участие
     * в событии {@link List}<{@link ParticipationRequestDto}>.
     */
    @GetMapping("/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> getUserRequest(@PathVariable @Min(0) Integer userId,
                                                 @PathVariable @Min(0) Integer eventId) {
        return service.getUserRequest(userId, eventId);
    }

    /**
     * Подтверждение чужой заявки на участие в событии текущего пользователя.
     * Если лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется.
     * Нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие.
     * Если при подтверждении данной заявки, лимит заявок для события исчерпан, то все
     * неподтверждённые заявки отклоняются.
     * @param userId id пользователя.
     * @param eventId id события.
     * @param reqId id заявки.
     * @return возвращает подтвержденную заявку на участие {@link ParticipationRequestDto}.
     */
    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmRequest(@PathVariable @Min(0) Integer userId,
                                           @PathVariable @Min(0) Integer eventId,
                                           @PathVariable @Min(0) Integer reqId) {
        return service.confirmRequest(userId, eventId, reqId);
    }

    /**
     * Отклонение чужой заявки на участие в событии текущего пользователя.
     * @param userId id пользователя.
     * @param eventId id события.
     * @param reqId id заявки.
     * @return возвращает отклоненную заявку на участие {@link ParticipationRequestDto}.
     */
    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectRequest(@PathVariable @Min(0) Integer userId,
                                          @PathVariable @Min(0) Integer eventId,
                                          @PathVariable @Min(0) Integer reqId) {
        return service.rejectRequest(userId, eventId, reqId);
    }

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях.
     * @param userId id пользователя.
     * @return возвращает информации о заявках текущего пользователя на участие в чужих событиях
     * {@link List}<{@link ParticipationRequestDto}>.
     */
    @GetMapping("/{userId}/requests")
    List<ParticipationRequestDto> getUserRequests(@PathVariable @Min(0) Integer userId) {
        return service.getUserRequests(userId);
    }

    /**
     * Добавление запроса от текущего пользователя на участие в событии.
     * Нельзя добавить повторный запрос.
     * Инициатор события не может добавить запрос на участие в своём событии.
     * Нельзя участвовать в неопубликованном событии.
     * Если у события достигнут лимит запросов на участие - необходимо вернуть ошибку.
     *  Если для события отключена пре-модерация запросов на участие, то запрос переходит в подтвержденный.
     * @param userId id пользователя.
     * @param eventId id события.
     * @return возвращает созданную заявку на участие пользователя в указанном событии
     * {@link ParticipationRequestDto}
     */
    @PostMapping("/{userId}/requests")
    ParticipationRequestDto addRequest(@PathVariable @Min(0) Integer userId,
                                       @RequestParam @Min(0) Integer eventId) {
        return service.addRequest(userId, eventId);
    }

    /**
     * Отмена своего запроса на участие в событии.
     * @param userId id пользователя.
     * @param requestId id заявки.
     * @return возвращает отмененную заявку пользователя {@link ParticipationRequestDto}.
     */
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable @Min(0) Integer userId,
                                          @PathVariable @Min(0) Integer requestId) {
        return service.cancelRequest(userId, requestId);
    }
}
