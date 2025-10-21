package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
}
