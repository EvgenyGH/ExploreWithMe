package ru.practicum.ewmmain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmain.user.model.User;
import ru.practicum.ewmmain.user.model.UserDto;
import ru.practicum.ewmmain.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    //Добавление нового пользователя
    @PostMapping("/users")
    User addUser(@RequestBody @Valid UserDto userDto) {
        return userService.addUser(userDto);
    }

    //Получение информации о пользователях
    //ids - id запрашиваемых пользователей
    //from - количество элементов, которые нужно пропустить для формирования текущего набора
    //to - количество элементов в наборе
    @GetMapping("/users")
    List<User> getUsers(@RequestParam(required = false) List<Integer> ids,
                        @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                        @RequestParam(required = false, defaultValue = "10") @Min(0) Integer size) {
        return userService.getUsers(ids, from, size);
    }

    //Удаление пользователя
    @DeleteMapping("/users/{userId}")
    void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
