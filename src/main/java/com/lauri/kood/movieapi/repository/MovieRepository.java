package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    // Returns a paginated list of movies whose titles contain the given string (case-insensitive)
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Returns a paginated list of movies in which the actor with the specified ID appears
    Page<Movie> findByActors_Id(Long actorId, Pageable pageable);

    // Returns a paginated list of movies that belong to the genre with the specified ID
    Page<Movie> findByGenres_Id(Long genreId, Pageable pageable);
}
