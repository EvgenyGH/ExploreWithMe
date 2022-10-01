package ru.practicum.ewmmain.controller.client.participationrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.model.participationrequest.dto.ParticipationRequestDto;
import ru.practicum.ewmmain.service.participationrequest.ParticipationReqService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class ParticipationReqControllerClient {
    private final ParticipationReqService service;

    //Получение информации о запросах на участие в событии текущего пользователя.
    @GetMapping("/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> getUserRequest(@PathVariable @Min(0) Integer userId,
                                                 @PathVariable @Min(0) Integer eventId) {
        return service.getUserRequest(userId, eventId);
    }

    //Подтверждение чужой заявки на участие в событии текущего пользователя.
    //Если лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется.
    //Нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие.
    //Если при подтверждении данной заявки, лимит заявок для события исчерпан, то все
    //неподтверждённые заявки отклоняются.
    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmRequest(@PathVariable @Min(0) Integer userId,
                                           @PathVariable @Min(0) Integer eventId,
                                           @PathVariable @Min(0) Integer reqId) {
        return service.confirmRequest(userId, eventId, reqId);
    }

    //Отклонение чужой заявки на участие в событии текущего пользователя.
    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectRequest(@PathVariable @Min(0) Integer userId,
                                          @PathVariable @Min(0) Integer eventId,
                                          @PathVariable @Min(0) Integer reqId) {
        return service.rejectRequest(userId, eventId, reqId);
    }

    //Получение информации о заявках текущего пользователя на участие в чужих событиях.
    @GetMapping("/{userId}/requests")
    List<ParticipationRequestDto> getUserRequests(@PathVariable @Min(0) Integer userId) {
        return service.getUserRequests(userId);
    }

    //Добавление запроса от текущего пользователя на участие в событии.
    //Нельзя добавить повторный запрос.
    //Инициатор события не может добавить запрос на участие в своём событии.
    //Нельзя участвовать в неопубликованном событии.
    //Если у события достигнут лимит запросов на участие - необходимо вернуть ошибку.
    //Если для события отключена пре-модерация запросов на участие, то запрос переходит в подтвержденный.
    @PostMapping("/{userId}/requests")
    ParticipationRequestDto addRequest(@PathVariable @Min(0) Integer userId,
                                       @RequestParam @Min(0) Integer eventId) {
        return service.addRequest(userId, eventId);
    }

    //Отмена своего запроса на участие в событии.
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable @Min(0) Integer userId,
                                          @PathVariable @Min(0) Integer requestId) {
        return service.cancelRequest(userId, requestId);
    }
}
