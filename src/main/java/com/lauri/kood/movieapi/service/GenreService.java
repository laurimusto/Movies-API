package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.dto.ActorPatchDTO;
import com.lauri.kood.movieapi.dto.GenrePatchDTO;
import com.lauri.kood.movieapi.dto.GenreResponseDTO;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.exceptions.IllegalStateException;
import com.lauri.kood.movieapi.exceptions.ResourceNotFoundException;
import com.lauri.kood.movieapi.mapper.GenreMapper;
import com.lauri.kood.movieapi.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository; //makes our repository immutable

    public GenreService(GenreRepository genreRepository) { //constructor injection
        this.genreRepository = genreRepository;
    }

    private GenreResponseDTO toResponse(Genre genre) {
        return new GenreResponseDTO(genre.getId(), genre.getName());
    }

    public GenreResponseDTO create(GenrePatchDTO genreDto) {
        Genre genre = new Genre();
        genre.setName(genreDto.name());
        Genre savedGenre = genreRepository.save(genre);
        return toResponse(savedGenre);
    }

    public Set<GenreResponseDTO> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(genre -> new GenreResponseDTO(genre.getId(), genre.getName()))
                .collect(Collectors.toSet()); //retrieve all genres from database

    }

    public GenreResponseDTO findById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Can't find genre with " + id + " id")); //retrieve genre by id
        return toResponse(genre);
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

    public void delete(Long id, boolean force) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Can't find genre with id: " + id));
        boolean hasRelations = !genre.getMovies().isEmpty();
        if(hasRelations && !force) {
            throw new IllegalStateException("Cannot delete genre with related movies. Use force=true to override.");
        }
        genreRepository.delete(genre);
        System.out.println("Deleted genre with id " + id + " which is " + genre.getName());
    }

    public GenreResponseDTO updateGenre(Long id, GenrePatchDTO genreDto) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Can't find genre with id: " + id));
        genre.setName(genreDto.name());
        Genre updatedGenre = genreRepository.save(genre);
        return GenreMapper.toGenreResponseDto(updatedGenre);
    }

}

