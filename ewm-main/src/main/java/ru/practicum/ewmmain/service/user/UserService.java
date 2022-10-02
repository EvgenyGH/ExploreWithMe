package ru.practicum.ewmmain.service.user;

import ru.practicum.ewmmain.model.user.User;
import ru.practicum.ewmmain.model.user.dto.UserDto;
import ru.practicum.ewmmain.model.user.dto.UserNewDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с {@link User}
 * @author Evgeny S
 * @see UserServiceImpl
 */
public interface UserService {
    /**
     * Добавление нового пользователя.
     * @param userNewDto данные для создания нового пользователя.
     * @return возвращает созданного пользователя {@link UserDto}.
     */
    UserDto addUser(UserNewDto userNewDto);

    /**
     * Получение информации о пользователях.
     * @param ids  id запрашиваемых пользователей.
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора.
     * @param size количество элементов в наборе.
     * @return возвращает список пользователей {@link List}<{@link UserDto}>.
     */
    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    /**
     * Удаление пользователя
     * @param userId id пользователя.
     */
    void deleteUser(Integer userId);

    /**
     * Получение пользователя по id.
     * @param userId id пользователя.
     * @return возвращает пользователя по id.
     */
    User getUserById(Integer userId);
}
