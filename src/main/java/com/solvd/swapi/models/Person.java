package com.solvd.swapi.models;

public class Person {
    private String Id;
    private String name;
    private String birthYear;
    private String gender;
    private String hairColor;
    private int height;

    public Person(String id, String name, String birthYear, String gender, String hairColor, int height) {
        Id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
        this.hairColor = hairColor;
        this.height = height;
    }

    public Person() {
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

    @Override
    public String toString() {
        return "Person{" +
                "Id='" + Id + '\'' +
                ", name='" + name + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", gender='" + gender + '\'' +
                ", hairColor='" + hairColor + '\'' +
                ", height=" + height +
                '}';
    }
}
