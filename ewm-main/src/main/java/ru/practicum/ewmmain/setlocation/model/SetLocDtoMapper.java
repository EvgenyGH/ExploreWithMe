package ru.practicum.ewmmain.setlocation.model;

public class SetLocDtoMapper {
    public static SetLocationDto toDto(SetLocation location) {
        return new SetLocationDto(location.getId(), location.getName(), location.getDescription(),
                location.getLatitude(), location.getLongitude(), location.getRadius());
    }

    public static SetLocation toSetLocation(SetLocationDto locationDto) {
        return new SetLocation(locationDto.getId(), locationDto.getName(), locationDto.getDescription(),
                locationDto.getLatitude(), locationDto.getLongitude(), locationDto.getRadius());
    }
}
