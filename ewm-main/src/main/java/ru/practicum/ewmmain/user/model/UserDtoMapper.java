package ru.practicum.ewmmain.user.model;

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
