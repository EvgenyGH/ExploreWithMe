package ru.practicum.ewmmain.service.participationrequest;

import ru.practicum.ewmmain.model.participationrequest.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с {@link ru.practicum.ewmmain.model.participationrequest.ParticipationRequest}
 * @author Evgeny S
 * @see ParticipationReqServiceImpl
 */
public interface ParticipationReqService {
    /**
     * Получение информации о запросах на участие в событии текущего пользователя.
     * @param userId id пользователя.
     * @param eventId id события.
     * @return возвращает список запросов пользователя на участие
     * в событии {@link List}<{@link ParticipationRequestDto}>.
     */
    List<ParticipationRequestDto> getUserRequest(Integer userId, Integer eventId);

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
    ParticipationRequestDto confirmRequest(Integer userId, Integer eventId, Integer reqId);

    /**
     * Отклонение чужой заявки на участие в событии текущего пользователя.
     * @param userId id пользователя.
     * @param eventId id события.
     * @param reqId id заявки.
     * @return возвращает отклоненную заявку на участие {@link ParticipationRequestDto}.
     */
    ParticipationRequestDto rejectRequest(Integer userId, Integer eventId, Integer reqId);

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях.
     * @param userId id пользователя.
     * @return возвращает информации о заявках текущего пользователя на участие в чужих событиях
     * {@link List}<{@link ParticipationRequestDto}>.
     */
    List<ParticipationRequestDto> getUserRequests(Integer userId);

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
    ParticipationRequestDto addRequest(Integer userId, Integer eventId);

    /**
     * Отмена своего запроса на участие в событии.
     * @param userId id пользователя.
     * @param requestId id заявки.
     * @return возвращает отмененную заявку пользователя {@link ParticipationRequestDto}.
     */
    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);

    /**
     * Получить количество подтвержденных запросов на событие.
     * @param eventId id события.
     * @return возвращает количество подтвержденных запросов на событие.
     */
    Integer getConfRequests(Integer eventId);
}
