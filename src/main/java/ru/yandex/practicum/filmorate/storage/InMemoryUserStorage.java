package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer userId = 1;

    @Override
    public User createUser(User user) {
        user.setId(userId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public Collection<User> getUserFriends(Integer id) {
        return users.get(id).getFriends()
                .stream()
                .map(this::getById)
                .collect(Collectors.toSet());
    }

    @Override
    public User getById(Integer id) {
        return users.get(id);
    }
}
