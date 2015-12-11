package com.splunk.hollywood.dao;

import com.splunk.hollywood.model.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieDAO extends PagingAndSortingRepository<Movie, Integer> {
    Movie findByMovieId(int movieId);
}
