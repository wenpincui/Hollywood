package com.splunk.hollywood.controller;

import com.splunk.hollywood.dao.MovieDAO;
import com.splunk.hollywood.dao.RatingDAO;
import com.splunk.hollywood.dto.MovieDTO;
import com.splunk.hollywood.model.Movie;
import com.splunk.hollywood.utils.FloatRounder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movies")
public class MovieInfo {
    private MovieDAO movieDAO;
    private RatingDAO ratingDAO;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public MovieDTO getMovieDetail(@PathVariable int id) {
        Movie movie = movieDAO.findByMovieId(id);

        MovieDTO dto = new MovieDTO();
        BeanUtils.copyProperties(movie, dto);
        dto.setImbdLinks(movie.getLinks().getImdbId());
        dto.setTmdbLinks(movie.getLinks().getTmdbId());

        float ratings = ratingDAO.findAverageByVideoId(id);
        dto.setRatings(FloatRounder.floor(ratings));

        return dto;
    }

    @Autowired
    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Autowired
    public void setRatingDAO(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }
}
