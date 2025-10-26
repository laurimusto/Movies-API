package com.lauri.kood.movieapi.controller;

import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.repository.GenreRepository;
import com.lauri.kood.movieapi.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/genre")
    List<Genre> All() {
        return genreService.findAll();

    }
}
