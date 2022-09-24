package ru.practicum.ewmmain.user.service;

import ru.practicum.ewmmain.user.model.User;
import ru.practicum.ewmmain.user.model.UserDto;
import ru.practicum.ewmmain.user.model.UserNewDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserNewDto userNewDto);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);

    User getUserById(Integer userId);
}
