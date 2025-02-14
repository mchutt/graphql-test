package com.solvd.swapi.models;

public class Person {
    private String Id;
    private String name;
    private String birthYear;
    private String eyeColor;
    private String gender;
    private String hairColor;
    private int height;
    private int mass;
    private String skinColor;
    private Planet homeworld;
    private FilmConnection filmConnection;
    private Species species;
    private StarshipConnection starshipConnection;

    public Person() {
    }

    public Person(String id, String name, String birthYear, String eyeColor, String gender, String hairColor, int height, int mass, String skinColor, Planet homeworld, FilmConnection filmConnection, Species species, StarshipConnection starshipConnection) {
        Id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.eyeColor = eyeColor;
        this.gender = gender;
        this.hairColor = hairColor;
        this.height = height;
        this.mass = mass;
        this.skinColor = skinColor;
        this.homeworld = homeworld;
        this.filmConnection = filmConnection;
        this.species = species;
        this.starshipConnection = starshipConnection;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public void setHomeworld(Planet homeworld) {
        this.homeworld = homeworld;
    }

    public Planet getHomeworld() {
        return homeworld;
    }

    public FilmConnection getFilmConnection() {
        return filmConnection;
    }

    public void setFilmConnection(FilmConnection filmConnection) {
        this.filmConnection = filmConnection;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public StarshipConnection getStarshipConnection() {
        return starshipConnection;
    }

    public void setStarshipConnection(StarshipConnection starshipConnection) {
        this.starshipConnection = starshipConnection;
    }
}
