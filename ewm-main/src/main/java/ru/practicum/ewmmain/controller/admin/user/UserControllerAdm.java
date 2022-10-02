package ru.practicum.ewmmain.controller.admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.model.user.dto.UserDto;
import ru.practicum.ewmmain.model.user.dto.UserNewDto;
import ru.practicum.ewmmain.service.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Закрытый контроллер пользователей({@link ru.practicum.ewmmain.model.user.User})
 * для администратора.
 * @author Evgeny S
 */
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Validated
public class UserControllerAdm {
    /**
     * Сервис для работы с пользователями.
     */
    private final UserService userService;

    /**
     * Добавление нового пользователя.
     * @param userDto данные для создания нового пользователя.
     * @return возвращает созданного пользователя {@link UserDto}.
     */
    @PostMapping
    UserDto addUser(@RequestBody @Valid UserNewDto userDto) {
        return userService.addUser(userDto);
    }

    /**
     * Получение информации о пользователях.
     * @param ids  id запрашиваемых пользователей.
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора.
     * @param size количество элементов в наборе.
     * @return возвращает список пользователей {@link List}<{@link UserDto}>.
     */
    @GetMapping
    List<UserDto> getUsers(@RequestParam(required = false) List<Integer> ids,
                           @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                           @RequestParam(required = false, defaultValue = "10") @Min(0) Integer size) {
        return userService.getUsers(ids, from, size);
    }

    /**
     * Удаление пользователя
     * @param userId id пользователя.
     */
    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
