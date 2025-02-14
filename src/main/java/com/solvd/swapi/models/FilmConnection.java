package com.solvd.swapi.models;

import java.util.List;

public class FilmConnection {
    private List<Film> films;

    public FilmConnection() {
    }

    public FilmConnection(List<Film> films) {
        this.films = films;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
