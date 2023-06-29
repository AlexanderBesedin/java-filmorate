package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<Film>> violations;
    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer filmId = 1;

    @PostMapping //("/post")
    public Film create(@RequestBody @Valid Film film) {
        violations = validator.validate(film);
        if (!violations.isEmpty()) return film;

        film.setId(filmId++);
        films.put(film.getId(), film);
        log.info("Film {} has been CREATED", film);
        return film;
    }

    @PutMapping //("/update")
    public Film update(@RequestBody @Valid Film film) {
        violations = validator.validate(film);

        if (film.getId() == null) throw new ValidationException("Выполните post-запрос");
        if (!films.containsKey(film.getId())) {
            throw new ValidationException(String.format("Фильм с ID = %d не существует", film.getId()));
        }
        if (!violations.isEmpty()) return film;

        films.put(film.getId(), film);
        log.info("Film {} has been UPDATED", film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

}
