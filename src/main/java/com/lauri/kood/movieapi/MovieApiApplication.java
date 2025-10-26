package com.lauri.kood.movieapi;

import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.entity.Movie;
import com.lauri.kood.movieapi.repository.ActorRepository;
import com.lauri.kood.movieapi.repository.GenreRepository;
import com.lauri.kood.movieapi.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class MovieApiApplication {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    public MovieApiApplication(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MovieApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ActorRepository actorrepository, GenreRepository genreRepository, MovieRepository movieRepository, ActorRepository actorRepository) { //returns
        return (args -> {
            insertJavaAdvocates(actorRepository);
            System.out.println(actorrepository.findAll());
            System.out.println("DB absolute path: " + new java.io.File("movie.db").getAbsolutePath());

        });
    }

    private void insertJavaAdvocates(ActorRepository repository) {
        repository.save(new Actor("Lauri", "1995-09-14"));
        repository.save(new Actor("Mihkel", "1992-12-11"));
        repository.save(new Actor("Siiri", "1925-02-14"));
        repository.save(new Actor("Simba", "1895-05-14"));
        genreRepository.save(new Genre("Action"));
        genreRepository.save(new Genre("Comedy"));
        genreRepository.save(new Genre("Romance"));
        movieRepository.save(new Movie("Die hard 1", "1995", "10"));
        movieRepository.save(new Movie("Die hard 2", "2005", "12"));
        movieRepository.save(new Movie("Die hard 3", "1995", "15"));
    }


}