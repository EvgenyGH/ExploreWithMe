package ru.practicum.ewmmain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.user.exception.UserNotFoundException;
import ru.practicum.ewmmain.user.model.User;
import ru.practicum.ewmmain.user.model.UserDto;
import ru.practicum.ewmmain.user.model.UserDtoMapper;
import ru.practicum.ewmmain.user.model.UserNewDto;
import ru.practicum.ewmmain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserDto addUser(UserNewDto userNewDto) {
        User user = repository.save(UserDtoMapper.toUser(userNewDto));
        log.trace("New User added : {}", user);
        return UserDtoMapper.toDto(user);
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        List<User> users;

        if (ids != null) {
            users = repository.findAllById(ids);
        } else {
            users = repository.findAll(PageRequest.of(from / size, size)).toList();
        }

        log.trace("Found {} users", users.size());

        return users.stream().map(UserDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        repository.deleteById(userId);
        log.trace("User id={} deleted", userId);
    }

    @Override
    public User getUserById(Integer userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User id=%d not found", userId)));

        log.trace("{} Found user id={} : {}", LocalDateTime.now(), userId, user);

        return user;
    }
}
