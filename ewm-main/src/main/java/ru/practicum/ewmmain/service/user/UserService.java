package ru.practicum.ewmmain.service.user;

import ru.practicum.ewmmain.model.user.User;
import ru.practicum.ewmmain.model.user.dto.UserDto;
import ru.practicum.ewmmain.model.user.dto.UserNewDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserNewDto userNewDto);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);

    User getUserById(Integer userId);
}
