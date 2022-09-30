package ru.practicum.ewmmain.setlocation.service;

import ru.practicum.ewmmain.event.controller.SortOption;
import ru.practicum.ewmmain.event.model.event.EventDtoShort;
import ru.practicum.ewmmain.event.model.event.State;
import ru.practicum.ewmmain.setlocation.model.SetLocationDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SetLocationService {
    SetLocationDto addLocation(SetLocationDto locationDto);

    SetLocationDto getLocationById(Integer locId);

    List<SetLocationDto> getAllLocations(Integer from, Integer size);

    List<EventDtoShort> getEventsInLocation(Integer locId, SortOption sort, State state,
                                            String text, List<Integer> categories, Boolean paid,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            Boolean onlyAvailable, Integer from, Integer size);

    SetLocationDto updateDto(Integer locId, SetLocationDto locationDto);

    void deleteLocation(Integer locId);
}
