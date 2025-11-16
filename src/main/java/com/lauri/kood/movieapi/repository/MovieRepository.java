package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitleIgnoreCase(String title); //where name=?

    Optional<Movie> findByTitleLikeIgnoreCase(String title); //where name like '%name%'


}
