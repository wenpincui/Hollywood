package com.splunk.hollywood.controller;

import com.splunk.hollywood.dao.MovieDAO;
import com.splunk.hollywood.dao.RatingDAO;
import com.splunk.hollywood.dao.TagsDAO;
import com.splunk.hollywood.dto.MovieDTO;
import com.splunk.hollywood.exception.NotFoundException;
import com.splunk.hollywood.model.Movie;
import com.splunk.hollywood.model.Tag;
import com.splunk.hollywood.utils.FloatRounder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/movies")
public class MovieInfo {
    private MovieDAO movieDAO;
    private RatingDAO ratingDAO;
    private TagsDAO tagsDAO;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public MovieDTO getMovieDetail(@PathVariable int id) throws Exception {
        Movie movie = movieDAO.findByMovieId(id);

        if (movie == null) throw new NotFoundException();

        MovieDTO dto = new MovieDTO();
        BeanUtils.copyProperties(movie, dto);
        dto.setImbdLinks(movie.getLinks().getImdbId());
        dto.setTmdbLinks(movie.getLinks().getTmdbId());

        float ratings = ratingDAO.findAverageByMovieId(id);
        dto.setRatings(FloatRounder.floor(ratings));

        TreeMap<String, Integer> maps = new TreeMap<String, Integer>();
        List<Tag> tags = tagsDAO.findByMovieId(id);
        for (Tag tag : tags) {
            String t = tag.getTag();
            if (maps.containsKey(t)) {
                int count = maps.get(t);
                maps.put(t, count + 1);
            } else {
                maps.put(t, 1);
            }
        }

        dto.setTags(new ArrayList<String>());
        int maxTags = 5;
        for (String tag : maps.descendingKeySet()) {
            if (maxTags-- > 0) {
                dto.getTags().add(tag);
            } else {
                break;
            }
        }

        return dto;
    }

    @RequestMapping(value = "action/apropos", method = RequestMethod.POST)
    public List<MovieDTO> apropos(@PathVariable String name) throws Exception {
        List<Movie> movies = movieDAO.findByMovieName(name);

        List<MovieDTO> dtos = new ArrayList<MovieDTO>();

        for (Movie m : movies) {
            dtos.add(getMovieDetail(m.getMovieId()));
        }

        return dtos;
    }

    @Autowired
    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Autowired
    public void setRatingDAO(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }

    @Autowired
    public void setTagsDAO(TagsDAO tagsDAO) {
        this.tagsDAO = tagsDAO;
    }
}
