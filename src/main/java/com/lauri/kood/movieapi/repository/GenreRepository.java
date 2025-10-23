package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
