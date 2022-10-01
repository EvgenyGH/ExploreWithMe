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

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Validated
public class UserControllerAdm {
    private final UserService userService;

    //Добавление нового пользователя
    @PostMapping
    UserDto addUser(@RequestBody @Valid UserNewDto userDto) {
        return userService.addUser(userDto);
    }

    //Получение информации о пользователях
    //ids - id запрашиваемых пользователей
    //from - количество элементов, которые нужно пропустить для формирования текущего набора
    //to - количество элементов в наборе
    @GetMapping
    List<UserDto> getUsers(@RequestParam(required = false) List<Integer> ids,
                           @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                           @RequestParam(required = false, defaultValue = "10") @Min(0) Integer size) {
        return userService.getUsers(ids, from, size);
    }

    //Удаление пользователя
    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
