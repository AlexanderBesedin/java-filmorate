package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validator.IsAfterDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

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

    public Film(String name, String description, int duration, LocalDate releaseDate) { //конструктор для post-запроса
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }
}
