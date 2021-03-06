package com.splunk.hollywood.dto;

import java.util.List;

public class MovieDTO {
    private int movieId;
    private String title;
    private String genres;
    private String ratings;
    private int imbdLinks;
    private int tmdbLinks;
    private List<String> tags;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public int getImbdLinks() {
        return imbdLinks;
    }

    public void setImbdLinks(int imbdLinks) {
        this.imbdLinks = imbdLinks;
    }

    public int getTmdbLinks() {
        return tmdbLinks;
    }

    public void setTmdbLinks(int tmdbLinks) {
        this.tmdbLinks = tmdbLinks;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
