package com.solvd.swapi.films;

import java.util.List;

public class Film {
    private String title;
    private int episodeID;
    private String openingCrawl;
    private String releaseDate;
    private String director;
    private List<String> producers;

    public Film(String title, int episodeID, String openingCrawl, String releaseDate, String director, List<String> producers) {
        this.title = title;
        this.episodeID = episodeID;
        this.openingCrawl = openingCrawl;
        this.releaseDate = releaseDate;
        this.director = director;
        this.producers = producers;
    }

    public Film() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisodeID() {
        return episodeID;
    }

    public void setEpisodeID(int episodeID) {
        this.episodeID = episodeID;
    }

    public String getOpeningCrawl() {
        return openingCrawl;
    }

    public void setOpeningCrawl(String openingCrawl) {
        this.openingCrawl = openingCrawl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getProducers() {
        return producers;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", episodeID=" + episodeID +
                ", openingCrawl='" + openingCrawl + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", director='" + director + '\'' +
                ", producers=" + producers +
                '}';
    }

}
