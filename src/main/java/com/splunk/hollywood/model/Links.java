package com.splunk.hollywood.model;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class Links extends BaseModel {
    private int imdbId;
    private int tmdbId;
    private Movie movie;

    @Column(name = "imdbid")
    public int getImdbId() {
        return imdbId;
    }

    public void setImdbId(int imdbId) {
        this.imdbId = imdbId;
    }

    @Column(name = "tmdbid")
    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    @OneToOne
    @JoinColumn(name = "movieid")
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
