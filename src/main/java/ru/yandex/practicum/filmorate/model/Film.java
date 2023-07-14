package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validator.IsAfterDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * название не может быть пустым;
 * максимальная длина описания — 200 символов;
 * дата релиза — не раньше 28 декабря 1895 года;
 * продолжительность фильма должна быть положительной.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Integer id;
    @NotBlank(message = "Название фильма должно быть заполнено")
    private String name;
    @Size(max = 200, message = "Описание фильма должно быть не более 200 символов")
    private String description;
    @Positive(message = "Длительность фильма должна быть положительным числом")
    private int duration;
    @IsAfterDate(value = "1895-12-28", message = "Дата релиза не может быть ранее 1895-12-28")
    private LocalDate releaseDate;
    @NotNull
    private Mpa mpa;
    private Set<Genre> genres;
    @Setter(AccessLevel.NONE)
    private Set<Integer> likes = new HashSet<>();

    public Film(Integer id, String name, String description, int duration, LocalDate releaseDate, Mpa mpa, Set<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.mpa = mpa;
        this.genres = genres;
    }
}
