package ru.practicum.ewmmain.utils.mapper;

import ru.practicum.ewmmain.model.event.location.Location;
import ru.practicum.ewmmain.model.event.location.dto.LocationDto;

/**
 * Маппер класса {@link Location}
 * @author Evgeny S
 */
public class LocationDtoMapper {
    /**
     Маппер в класс {@link Location}
     * @param locationDto экземпляр класса для конвертации.
     * @return возвращает конвертированный в класс {@link Location} экземпляр.
     */
    public static Location toLocation(LocationDto locationDto) {
        return new Location(null, locationDto.getLat(), locationDto.getLon());
    }

    /**
     Маппер в класс {@link LocationDto}
     * @param location экземпляр класса для конвертации.
     * @return возвращает конвертированный в класс {@link LocationDto} экземпляр.
     */
    public static LocationDto toDto(Location location) {
        return new LocationDto(location.getLatitude(), location.getLongitude());
    }
}
