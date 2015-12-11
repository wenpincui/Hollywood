package com.splunk.hollywood.dao;

import com.splunk.hollywood.model.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RatingDAO extends PagingAndSortingRepository<Rating, Integer> {
    @Query("SELECT AVG(r.rating) from Rating r where r.movieId=:movieId")
    float findAverageByMovieId(@Param("movieId") int movieId);
}
