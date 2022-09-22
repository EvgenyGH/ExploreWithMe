package ru.practicum.ewmmain.user.service;

import ru.practicum.ewmmain.user.model.User;
import ru.practicum.ewmmain.user.model.UserDto;

import java.util.List;

public interface UserService {
    User addUser(UserDto userDto);

    List<User> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);
}
