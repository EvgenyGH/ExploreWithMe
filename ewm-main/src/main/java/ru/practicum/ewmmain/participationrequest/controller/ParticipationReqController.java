package ru.practicum.ewmmain.participationrequest.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipationReqController {
    //1	"GET /users/{userId}/events/{eventId}/requests Получение информации о запросах на участие в событии текущего пользователя"
            //2	"PATCH /users/{userId}/events/{eventId}/requests/{reqId}/confirm Подтверждение чужой заявки на участие в событии текущего пользователя"
            //3	"PATCH /users/{userId}/events/{eventId}/requests/{reqId}/reject Отклонение чужой заявки на участие в событии текущего пользователя"
            //4	"GET /users/{userId}/requests Получение информации о заявках текущего пользователя на участие в чужих событиях"
            //5	"POST /users/{userId}/requests Добавление запроса от текущего пользователя на участие в событии"
            //6	"PATCH /users/{userId}/requests/{requestId}/cancel Отмена своего запроса на участие в событии"
}
