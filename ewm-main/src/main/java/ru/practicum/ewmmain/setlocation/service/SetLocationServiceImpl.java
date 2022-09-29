package ru.practicum.ewmmain.setlocation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.client.StatisticsClient;
import ru.practicum.ewmmain.event.model.event.EventDtoShort;
import ru.practicum.ewmmain.event.repository.EventRepository;
import ru.practicum.ewmmain.participationrequest.service.ParticipationReqService;
import ru.practicum.ewmmain.setlocation.exception.SetLocationNotFoundException;
import ru.practicum.ewmmain.setlocation.model.SetLocDtoMapper;
import ru.practicum.ewmmain.setlocation.model.SetLocation;
import ru.practicum.ewmmain.setlocation.model.SetLocationDto;
import ru.practicum.ewmmain.setlocation.repository.SetLocationRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class SetLocationServiceImpl implements SetLocationService {
    private final SetLocationRepository repository;
    private final EventRepository eventRepository;
    private final ParticipationReqService reqService;
    private final StatisticsClient client;

    @Override
    public SetLocationDto addLocation(SetLocationDto locationDto) {
        SetLocation location = SetLocDtoMapper.toSetLocation(locationDto);
        location = repository.save(location);

        return SetLocDtoMapper.toDto(location);
    }

    @Override
    public SetLocationDto getLocationById(Integer locId) {
        SetLocation location = repository.findById(locId).orElseThrow(()->
                new SetLocationNotFoundException(String.format("Set location id=%d not found", locId)));

        return SetLocDtoMapper.toDto(location);
    }

    @Override
    public List<SetLocationDto> getAllLocations() {
        return repository.findAll().stream().map(SetLocDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDtoShort> getEventsInLocation(Integer locId, Integer eventId) {
        // TODO: 29.09.2022 implement
        return null;
    }

    @Override
    public SetLocationDto updateDto(Integer locId, SetLocationDto locationDto) {
        // TODO: 29.09.2022 dont forget to validate the entity
        return null;
    }

    @Override
    public void deleteLocation(Integer locId) {
        repository.deleteById(locId);
    }
}
