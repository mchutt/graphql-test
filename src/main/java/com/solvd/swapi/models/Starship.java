package com.solvd.swapi.models;

public class Starship {

    private String name;
    private String model;
    private String consumables;

    public Starship() {
    }

    public Starship(String name, String model, String consumables) {
        this.name = name;
        this.model = model;
        this.consumables = consumables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getConsumables() {
        return consumables;
    }

    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }
}
