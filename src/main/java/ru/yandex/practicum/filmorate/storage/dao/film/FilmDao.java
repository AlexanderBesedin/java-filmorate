package ru.yandex.practicum.filmorate.storage.dao.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

public interface FilmDao extends FilmStorage {
    List<Film> getCommonFilms(Integer userId, Integer friendId);

    boolean checkFilmExist(Integer id);
}
