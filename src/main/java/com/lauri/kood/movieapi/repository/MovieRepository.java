package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Movie> findByActors_Id(Long actorId, Pageable pageable);
    Page<Movie> findByGenres_Id(Long actorId, Pageable pageable);
}
