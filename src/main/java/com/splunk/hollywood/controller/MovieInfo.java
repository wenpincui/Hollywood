package com.splunk.hollywood.controller;

import com.splunk.hollywood.dao.MovieDAO;
import com.splunk.hollywood.dao.RatingDAO;
import com.splunk.hollywood.dao.TagsDAO;
import com.splunk.hollywood.dto.MovieDTO;
import com.splunk.hollywood.dto.PaginationResponse;
import com.splunk.hollywood.exception.NotFoundException;
import com.splunk.hollywood.exception.NotSupportException;
import com.splunk.hollywood.model.Links;
import com.splunk.hollywood.model.Movie;
import com.splunk.hollywood.model.Tag;
import com.splunk.hollywood.service.RecommendService;
import com.splunk.hollywood.utils.FloatRounder;
import com.typesafe.config.Config;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(value = "/movies")
public class MovieInfo {
    private MovieDAO movieDAO;
    private RatingDAO ratingDAO;
    private TagsDAO tagsDAO;
    private RecommendService recommendService;
    private Config config;

    @RequestMapping
    @Transactional(readOnly = true)
    public PaginationResponse<MovieDTO> getMovies(@RequestParam int page,
                                    @RequestParam int pageSize) throws Exception {
        Page<Movie> movies = movieDAO.findAll(new PageRequest(page - 1, pageSize));

        PaginationResponse<MovieDTO> response = new PaginationResponse<MovieDTO>();
        response.setPage(page);
        response.setPageSize(pageSize);
        response.setTotal(movies.getTotalElements());
        List<MovieDTO> dtos = new ArrayList<MovieDTO>();
        for (Movie m : movies.getContent()) {
            dtos.add(getMovieDetail(m.getMovieId()));
        }
        response.setData(dtos);

        return response;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public MovieDTO getMovieDetail(@PathVariable int id) throws Exception {
        Movie movie = movieDAO.findByMovieId(id);

        if (movie == null) throw new NotFoundException();

        MovieDTO dto = new MovieDTO();
        BeanUtils.copyProperties(movie, dto);

        if (movie.getLinks() != null) {
            Links link = movie.getLinks();
            dto.setImbdLinks(link.getImdbId());
            dto.setTmdbLinks(link.getTmdbId());
        }

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

    @RequestMapping(value = "/action/apropos", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public Callable<List<MovieDTO>> apropos(@RequestParam final String name) throws Exception {
        return new Callable<List<MovieDTO>>() {
            @Override
            public List<MovieDTO> call() throws Exception {
                List<Movie> movies = movieDAO.findByMovieName(name);

                List<MovieDTO> dtos = new ArrayList<MovieDTO>();

                for (Movie m : movies) {
                    dtos.add(getMovieDetail(m.getMovieId()));
                }

                return dtos;
            }
        };
    }

    @RequestMapping(value = "action/recommend", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public List<MovieDTO> recommend(@RequestParam(required = true) int userId,
                                    @RequestParam(defaultValue = "10") int num)
            throws Exception{

        if (config.getBoolean("hollywood.recommendEnable")) {
            List<MovieDTO> dtos = new ArrayList<MovieDTO>();
            for (int movieId : recommendService.recommend(userId, num)) {
                dtos.add(getMovieDetail(movieId));
            }

            return dtos;
        } else {
            throw new NotSupportException();
        }
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

    @Autowired
    public void setRecommendService(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
    }
}
