package ru.practicum.ewmmain.user.model;

public class UserDtoMapper {
    public static User toUser(UserDto userDto) {
        return new User(null, userDto.getName(), userDto.getEmail());
    }

    public static UserDtoShort toDtoShort(User user) {
        return new UserDtoShort(user.getId(), user.getName());
    }
}
