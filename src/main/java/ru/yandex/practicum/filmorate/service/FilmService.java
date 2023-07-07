package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public Film create(Film film) {
        log.info("Film {} has been CREATED", film);
        return filmStorage.createFilm(film);
    }

    public Film update(Film film) {
        if (film.getId() == null || filmStorage.getById(film.getId()) == null) {
            throw new NotFoundException("Невозможно обновить фильм c ID = null");
        }
        log.info("Film {} has been UPDATED", film);
        return filmStorage.updateFilm(film);
    }

    public void addLike(Integer filmId, Integer userId) {
        Film film = getFilm(filmId);
        userService.getUser(userId); // метод getUser() выбросит исключение, если userId не существует
        if (film.getLikes().contains(userId)) {
            throw new AlreadyExistException(
                    String.format("Пользователь с ID = %d уже ПОСТАВИЛ лайк фильму с ID = %d", userId, filmId));
        }

        film.getLikes().add(userId);
        log.info("Film with ID = {} was LIKED by user with ID = {}", filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = getFilm(filmId);
        userService.getUser(userId); // метод getUser() выбросит исключение, если userId не существует
        if (!film.getLikes().contains(userId)) {
            throw new NotFoundException(
                    String.format("Пользователь с ID = %d уже УДАЛИЛ лайк фильму с ID = %d", userId, filmId));
        }

        film.getLikes().remove(userId);
        log.info("Film with ID = {} was UNLIKED by user with ID = {}", filmId, userId);
    }

    public Film getFilm(Integer id) {
        if (filmStorage.getById(id) == null) {
            throw new NotFoundException(String.format("Фильм с ID = %d не существует", id));
        }
        log.info("Get a film with ID = {}", id);
        return filmStorage.getById(id);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Collection<Film> getTopFilms(Integer count) {
        int defaultValue = 10; // Если count == null
        int countFilms = filmStorage.getFilms().size();

        if (count == null) {
            if (countFilms < defaultValue) {
                log.info("Get {} popular films", countFilms);
                return filmStorage.getTopFilms(countFilms);
            } else {
                log.info("Get {} popular films", defaultValue);
                return filmStorage.getTopFilms(defaultValue);
            }
        } else {
            if (count <= 0)  {
                throw new ValidationException("Количество фильмов должно быть положительным числом");
            }
            if (count > countFilms) {
                throw new ValidationException(
                        "Количество фильмов не может быть более " + countFilms);
            }
            log.info("Get {} popular films", count);
            return filmStorage.getTopFilms(count);
        }
    }
}
