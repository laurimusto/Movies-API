package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.repository.GenreRepository;

import java.util.List;

public class GenreService {

    private final GenreRepository genreRepository; //makes our repository immutable

    public GenreService(GenreRepository genreRepository) { //constructor injection
        this.genreRepository = genreRepository;
    }

    public Genre create(Genre genre) {
        if (genre == null) {
            return null;
        }
        else {
            return genreRepository.save(genre);
        }
    }


}

