package com.splunk.hollywood.dao;

import com.splunk.hollywood.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieDAO extends PagingAndSortingRepository<Movie, Integer> {
    Movie findByMovieId(int movieId);

    @Query("SELECT m from Movie m where m.title like \'%:name%\'")
    List<Movie> findByMovieName(@Param("name") String name);
}
