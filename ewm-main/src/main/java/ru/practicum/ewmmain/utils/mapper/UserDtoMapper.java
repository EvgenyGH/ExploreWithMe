package ru.practicum.ewmmain.utils.mapper;

import ru.practicum.ewmmain.model.user.User;
import ru.practicum.ewmmain.model.user.dto.UserDto;
import ru.practicum.ewmmain.model.user.dto.UserNewDto;
import ru.practicum.ewmmain.model.user.dto.UserShortDto;

/**
 * Маппер класса {@link User}
 * @author Evgeny S
 */
public class UserDtoMapper {

    /**
     * Маппер в класс {@link User}
     * @param userNewDto экземпляр класса для конвертации.
     * @return возвращает конвертированный в класс {@link User} экземпляр.
     */
    public static User toUser(UserNewDto userNewDto) {
        return new User(null, userNewDto.getName(), userNewDto.getEmail());
    }

    /**
     * Маппер в класс {@link UserShortDto}
     * @param user экземпляр класса для конвертации.
     * @return возвращает конвертированный в класс {@link UserShortDto} экземпляр.
     */
    public static UserShortDto toDtoShort(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    /**
     * Маппер в класс {@link UserDto}
     * @param user экземпляр класса для конвертации.
     * @return возвращает конвертированный в класс {@link UserDto} экземпляр.
     */
    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
