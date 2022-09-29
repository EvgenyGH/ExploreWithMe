package ru.practicum.ewmmain.setlocation.service;

import ru.practicum.ewmmain.event.model.event.EventDtoShort;
import ru.practicum.ewmmain.setlocation.model.SetLocationDto;

import java.util.List;

public interface SetLocationService {
    SetLocationDto addLocation(SetLocationDto locationDto);

    SetLocationDto getLocationById(Integer locId);

    List<SetLocationDto> getAllLocations();

    List<EventDtoShort> getEventsInLocation(Integer locId, Integer eventId);

    SetLocationDto updateDto(Integer locId, SetLocationDto locationDto);

    void deleteLocation(Integer locId);
}
