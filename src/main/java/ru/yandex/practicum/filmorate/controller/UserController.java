package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<User>> violations;
    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer userId = 1;

    @PostMapping //("/post")
    public User create(@RequestBody @Valid User user) {
        violations = validator.validate(user);
        if (!violations.isEmpty()) return user;

        user.setId(userId++);
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
        users.put(user.getId(), user);
        log.info("User {} has been CREATED", user);
        return user;
    }

    @PutMapping //("/update")
    public User update(@RequestBody @Valid User user) {
        violations = validator.validate(user);
        if (user.getId() == null) throw new ValidationException("Выполните post-запрос");
        if (!users.containsKey(user.getId())) {
            throw new ValidationException(String.format("Пользователь с ID = %d не существует", user.getId()));
        }
        if (!violations.isEmpty()) return user;

        users.put(user.getId(), user);
        log.info("User {} has been UPDATED", user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
