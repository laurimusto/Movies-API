package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.dto.ActorPatchDTO;
import com.lauri.kood.movieapi.dto.GenrePatchDTO;
import com.lauri.kood.movieapi.dto.GenreResponseDTO;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.exceptions.ResourceNotFoundException;
import com.lauri.kood.movieapi.repository.GenreRepository;

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
            throw new NullPointerException("genre can't be null");
        } else {
            return genreRepository.save(genre); //create new genre
        }
    }

    public List<Genre> findAll() {
        return genreRepository.findAll(); //retrieve all genres from database

    }

    public Genre findById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(()
                -> new ResourceNotFoundException("Can't find genre with " + id + " id") ); //retrieve genre by id
    }

    public GenreResponseDTO update(Long id, ActorPatchDTO newName) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't add new name"));
        if (newName.name() != null && !newName.name().isBlank()) {
            genre.setName(newName.name());
        }

        genreRepository.save(genre);
        return new GenreResponseDTO(genre.getId(), genre.getName());
    }

    public GenrePatchDTO delete(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(()
                -> new ResourceNotFoundException("Can't find genre with " + id + " id") );
        genreRepository.delete(genre);
        return new GenrePatchDTO(genre.getId(), genre.getName());

    }

}

