package com.solvd.swapi.models;

public class Planet {
    private String name;

    public Planet() {
    }

    public Planet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
