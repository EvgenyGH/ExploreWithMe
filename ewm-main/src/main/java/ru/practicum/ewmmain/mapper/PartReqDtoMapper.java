package ru.practicum.ewmmain.mapper;

import ru.practicum.ewmmain.model.participationrequest.ParticipationRequest;
import ru.practicum.ewmmain.model.participationrequest.dto.ParticipationRequestDto;

public class PartReqDtoMapper {
    public static ParticipationRequestDto toDto(ParticipationRequest request) {
        return new ParticipationRequestDto(request.getId(), request.getEvent().getId(), request.getCreated(),
                request.getRequester().getId(), request.getStatus());
    }
}
