package com.splunk.hollywood.dao;

import com.splunk.hollywood.model.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagsDAO extends PagingAndSortingRepository<Tag, Integer> {
    List<Tag> findByMovieId(@Param("movieId") int movieId);
}
