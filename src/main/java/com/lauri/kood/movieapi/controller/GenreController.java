package com.lauri.kood.movieapi.controller;

import com.lauri.kood.movieapi.PaginationValidator;
import com.lauri.kood.movieapi.dto.*;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.repository.GenreRepository;
import com.lauri.kood.movieapi.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService, GenreRepository genreRepository) {
        this.genreService = genreService;
    }

    @GetMapping//returns http status 200 by default
    public Page<GenreResponseDTO> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "99") int size,
                                          @RequestParam(defaultValue = "name", required = false) String sortBy,
                                          @RequestParam(required = false, defaultValue = "true") boolean ascending) {
        //if ascending is true, then sort by ascending, otherwise sort by descending order.
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PaginationValidator.validate(page, size);
        Pageable pageable = PageRequest.of(page, size, sort);

        return genreService.findAll(pageable);
    }

    @GetMapping("/{id}") //returns httpstatus 200 by default
    public GenreResponseDTO findById(@PathVariable Long id) {
        return genreService.findById(id);
    }

    @GetMapping("/{id}/movies") //return all movies where actor has appeared
    public Page<MovieResponseDTO> getMoviesByGenres(@PathVariable Long id,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "99") int size) {
        PaginationValidator.validate(page, size);
        Pageable pageable = PageRequest.of(page, size);
        return genreService.getMoviesByGenres(id, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED) //returns httpstatus 201
    @PostMapping
    public GenreResponseDTO create(@RequestBody @Valid GenrePatchDTO genreDto) {
        return genreService.create(genreDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public GenreResponseDTO updateGenre(@PathVariable Long id,
                                        @RequestBody GenrePatchDTO genreDto) {
        return genreService.updateGenre(id, genreDto);
    }
@Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT) //does not return anything after deletion, httpstatus 204
    @DeleteMapping("/{id}")//should always update using ID but never expose ID to client.
    public void deleteGenre(@PathVariable Long id, @RequestParam(defaultValue = "false") Boolean force) {
        genreService.deleteGenre(id, force);
    }
}