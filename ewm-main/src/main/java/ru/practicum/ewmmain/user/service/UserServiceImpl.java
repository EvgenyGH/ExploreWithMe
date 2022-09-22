package ru.practicum.ewmmain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.user.model.User;
import ru.practicum.ewmmain.user.model.UserDto;
import ru.practicum.ewmmain.user.model.UserDtoMapper;
import ru.practicum.ewmmain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public User addUser(UserDto userDto) {
        User user = repository.save(UserDtoMapper.toUser(userDto));
        log.trace("Новый User добавлен: {}", user);
        return user;
    }

    @Override
    public List<User> getUsers(List<Integer> ids, Integer from, Integer size) {
        List<User> users;

        if (ids != null){
            users = repository.findAllById(ids);
        } else {
            users = repository.findAll(PageRequest.of(from/size, size)).toList();
        }

        log.trace("Найдено {} пользователей", users.size());

        return users;
    }

    @Override
    public void deleteUser(Integer userId) {
        repository.deleteById(userId);
        log.trace("Пользователь id={} удален", userId);
    }
}
