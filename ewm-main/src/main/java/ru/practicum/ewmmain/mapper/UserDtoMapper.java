package ru.practicum.ewmmain.mapper;

import ru.practicum.ewmmain.model.user.User;
import ru.practicum.ewmmain.model.user.dto.UserDto;
import ru.practicum.ewmmain.model.user.dto.UserNewDto;
import ru.practicum.ewmmain.model.user.dto.UserShortDto;

public class UserDtoMapper {

    public static User toUser(UserNewDto userNewDto) {
        return new User(null, userNewDto.getName(), userNewDto.getEmail());
    }

    public static UserShortDto toDtoShort(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
