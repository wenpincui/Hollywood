package com.splunk.hollywood.model;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie extends BaseModel {
    private int movieId;
    private String title;
    private String genres;
    private Links links;

    @Column(name = "movieid")
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

    @OneToOne(mappedBy = "movie")
    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
