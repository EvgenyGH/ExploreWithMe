package ru.practicum.ewmmain.participationrequest.model;

public class PartReqDtoMapper {
    public static ParticipationRequestDto toDto(ParticipationRequest request){
        return new ParticipationRequestDto(request.getId(), request.getEvent().getId(), request.getCreated(),
                request.getRequester().getId(), request.getStatus());
    }
}
