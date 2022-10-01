package ru.practicum.ewmmain.mapper;

import ru.practicum.ewmmain.model.event.location.Location;
import ru.practicum.ewmmain.model.event.location.dto.LocationDto;

public class LocationDtoMapper {
    public static Location toLocation(LocationDto locationDto) {
        return new Location(null, locationDto.getLat(), locationDto.getLon());
    }

    public static LocationDto toDto(Location location) {
        return new LocationDto(location.getLatitude(), location.getLongitude());
    }
}
