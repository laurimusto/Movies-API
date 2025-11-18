package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);

    List<Genre> findAllByMovies(Set<Movie> movies);

    List<Genre> findGenreByMoviesIn(Collection<Set<Movie>> movies);
}
