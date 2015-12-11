package com.splunk.hollywood.controller;

import com.splunk.hollywood.dao.MovieDAO;
import com.splunk.hollywood.dto.MovieDTO;
import com.splunk.hollywood.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movies")
public class MovieInfo {
    private MovieDAO movieDAO;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public MovieDTO getMovieDetail(@PathVariable int id) {
        Movie movie = movieDAO.findByMovieId(id);

        MovieDTO dto = new MovieDTO();
        dto.setMovieId(id);
        dto.setTitle(movie.getTitle());
        dto.setGenres(movie.getGenres());

        return dto;
    }

    @Autowired
    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }
}
