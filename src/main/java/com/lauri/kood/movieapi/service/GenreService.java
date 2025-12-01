package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.dto.GenrePatchDTO;
import com.lauri.kood.movieapi.dto.GenreResponseDTO;
import com.lauri.kood.movieapi.dto.MovieResponseDTO;
import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.entity.Movie;
import com.lauri.kood.movieapi.exceptions.ResourceInUseException;
import com.lauri.kood.movieapi.exceptions.ResourceNotFoundException;
import com.lauri.kood.movieapi.mapper.GenreMapper;
import com.lauri.kood.movieapi.mapper.MovieMapper;
import com.lauri.kood.movieapi.repository.GenreRepository;
import com.lauri.kood.movieapi.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GenreService {

    private final GenreRepository genreRepository;//makes our repository immutable
    private final MovieRepository movieRepository;

    public GenreService(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }

    public GenreResponseDTO create(GenrePatchDTO genreDto) {
        Genre genre = new Genre();
        genre.setName(genreDto.name());
        Genre savedGenre = genreRepository.save(genre);
        return GenreMapper.toGenreResponseDto(savedGenre);
    }

    public Page<GenreResponseDTO> getAll(Pageable pageable) {
        return genreRepository.findAll(pageable)
                .map(genre
                        -> new GenreResponseDTO(genre.getId(), genre.getName())); //retrieve all genres from database
    }

    public GenreResponseDTO findById(Long id) {
        Genre genre = genreRepository
                .findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Can't find genre with " + id + " id")); //Retrieve genre by id
        return GenreMapper.toGenreResponseDto(genre);
    }

//    public Page<MovieResponseDTO> getMoviesByActor(Long id, Pageable pageable) {
//        Actor actor = actorRepository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Actor with id " + id + " not found"));
//        return movieRepository.findByActors_Id(id, pageable)
//                .map(MovieMapper::toMovieResponseDTO);
//    }

    public Page<MovieResponseDTO> getMoviesByGenres(Long id, Pageable pageable) {
        Genre genre = genreRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Genre with id " + id + " not found."));
        return movieRepository.findByGenres_Id(id, pageable)
                .map(MovieMapper::toMovieResponseDTO);
    }

    @Transactional
    public void deleteGenre(Long id, boolean force) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Can't find genre with id: " + id));
        boolean hasRelations = !genre.getMovies().isEmpty();
        if (hasRelations && !force) {
            throw new ResourceInUseException("Cannot delete genre with related movies. Use ?force=true to override."); //Something is wrong here
        }

        if (force) {// If forced, clear the many-to-many relations properly
            genre.getMovies().forEach(movie -> movie.getGenres().remove(genre));// Remove Genre from each Movie
            genre.getMovies().clear();// Clear genre from movies list
        }
        genreRepository.delete(genre);
        System.out.println("Deleted genre with id " + id + " which is " + genre.getName());
    }

    @Transactional
    public GenreResponseDTO updateGenre(Long id, GenrePatchDTO genreDto) {
        Genre genre = genreRepository
                .findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Can't find genre with id: " + id));
        if (genreDto.name() != null && !genreDto.name().isBlank()) {
            genre.setName(genreDto.name());
        }

        Genre updatedGenre = genreRepository.save(genre);
        return GenreMapper.toGenreResponseDto(updatedGenre);
    }

}

