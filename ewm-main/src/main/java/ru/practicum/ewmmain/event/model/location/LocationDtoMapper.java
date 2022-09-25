package ru.practicum.ewmmain.event.model.location;

public class LocationDtoMapper {
    public static Location toLocation(LocationDto locationDto){
        return new Location(null, locationDto.getLat(), locationDto.getLon());
    }

    public static LocationDto toDto(Location location){
        return new LocationDto(location.getLatitude(), location.getLongitude());
    }
}
