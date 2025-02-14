package com.solvd.swapi.models;

import java.util.List;

public class StarshipConnection {
    private List<Starship> starships;

    public StarshipConnection() {
    }

    public StarshipConnection(List<Starship> starships) {
        this.starships = starships;
    }

    public List<Starship> getStarships() {
        return starships;
    }

    public void setStarships(List<Starship> starships) {
        this.starships = starships;
    }
}
