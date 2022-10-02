package ru.practicum.ewmmain.utils.mapper;

import ru.practicum.ewmmain.model.participationrequest.ParticipationRequest;
import ru.practicum.ewmmain.model.participationrequest.dto.ParticipationRequestDto;

/**
 * Маппер класса {@link ParticipationRequest}
 * @author Evgeny S
 */
public class PartReqDtoMapper {
    /**
     * Маппер в класс {@link ParticipationRequestDto}
     * @param request экземпляр класса для конвертации.
     * @return возвращает конвертированный в класс {@link ParticipationRequestDto} экземпляр.
     */
    public static ParticipationRequestDto toDto(ParticipationRequest request) {
        return new ParticipationRequestDto(request.getId(), request.getEvent().getId(), request.getCreated(),
                request.getRequester().getId(), request.getStatus());
    }
}
