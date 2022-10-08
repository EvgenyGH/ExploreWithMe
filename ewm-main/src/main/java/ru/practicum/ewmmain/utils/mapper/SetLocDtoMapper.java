package ru.practicum.ewmmain.utils.mapper;

import ru.practicum.ewmmain.model.setlocation.SetLocation;
import ru.practicum.ewmmain.model.setlocation.dto.SetLocationDto;

/**
 * Маппер {@link SetLocationDto} / {@link SetLocation}.
 *
 * @author Evgeny S
 * @see SetLocation
 * @see SetLocationDto
 */
public class SetLocDtoMapper {
    /**
     * Конвертировать {@link SetLocationDto} в {@link SetLocation}.
     * @param location локация для конвертации.
     * @return возвращает конвертированную в {@link SetLocationDto} локацию {@link SetLocation}.
     */
    public static SetLocationDto toDto(SetLocation location) {
        return new SetLocationDto(location.getId(), location.getName(), location.getDescription(),
                location.getLatitude(), location.getLongitude(), location.getRadius());
    }

    /**
     * Конвертировать {@link SetLocation} в {@link SetLocationDto}.
     * @param locationDto локация для конвертации.
     * @return возвращает конвертированную в {@link SetLocation} локацию {@link SetLocationDto}.
     */
    public static SetLocation toSetLocation(SetLocationDto locationDto) {
        return new SetLocation(locationDto.getId(), locationDto.getName(), locationDto.getDescription(),
                locationDto.getLatitude(), locationDto.getLongitude(), locationDto.getRadius());
    }
}
