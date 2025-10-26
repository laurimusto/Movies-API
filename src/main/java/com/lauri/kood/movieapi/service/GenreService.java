package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository; //makes our repository immutable

    public GenreService(GenreRepository genreRepository) { //constructor injection
        this.genreRepository = genreRepository;
    }

    public Genre create(Genre genre) {
        if (genre == null) {
            return null;
        } else {
            return genreRepository.save(genre); //create new genre
        }
    }

    public List<Genre> findAll() {
        return genreRepository.findAll(); //retrieve all genres from database

    }

    public Genre findById(Long id) {
        return genreRepository.findById(id).orElse(null); //retrieve genre by id
    }

    public Genre update(Long id, String newName) {
        Genre genre = genreRepository.findById(id).orElse(null);

        if (genre == null) {
            return null;
        }
        genre.setName(newName);
        return genreRepository.save(genre);


    }

}

