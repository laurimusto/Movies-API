package com.lauri.kood.movieapi;

import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.entity.Movie;
import com.lauri.kood.movieapi.repository.ActorRepository;
import com.lauri.kood.movieapi.repository.GenreRepository;
import com.lauri.kood.movieapi.repository.MovieRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder {

    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public DatabaseSeeder(GenreRepository genreRepository,
                          ActorRepository actorRepository,
                          MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void seedDatabase() {

        if (movieRepository.count() > 0) return; // Prevent duplicate seeding

        // --- Genres ---
        Genre action = new Genre("Action");
        Genre comedy = new Genre("Comedy");
        Genre drama = new Genre("Drama");
        Genre sciFi = new Genre("Sci-Fi");
        Genre thriller = new Genre("Thriller");

        genreRepository.saveAll(List.of(action, comedy, drama, sciFi, thriller));

        // --- Actors ---
        Actor leonardo = new Actor("Leonardo DiCaprio", LocalDate.parse("1974-11-11"));
        Actor tomHardy = new Actor("Tom Hardy", LocalDate.parse("1977-09-15"));
        Actor ellenPage = new Actor("Elliot Page", LocalDate.parse("1987-02-21"));
        Actor meryl = new Actor("Meryl Streep", LocalDate.parse("1949-06-22"));
        Actor jennifer = new Actor("Jennifer Lawrence", LocalDate.parse("1990-08-15"));
        Actor emma = new Actor("Emma Stone", LocalDate.parse("1988-11-06"));
        Actor samuel = new Actor("Samuel L. Jackson", LocalDate.parse("1948-12-21"));
        Actor hugh = new Actor("Hugh Jackman", LocalDate.parse("1968-10-12"));
        Actor natalie = new Actor("Natalie Portman", LocalDate.parse("1981-06-09"));
        Actor margot = new Actor("Margot Robbie", LocalDate.parse("1990-07-02"));
        Actor ryan = new Actor("Ryan Gosling", LocalDate.parse("1980-11-12"));
        Actor gal = new Actor("Gal Gadot", LocalDate.parse("1985-04-30"));
        Actor will = new Actor("Will Smith", LocalDate.parse("1968-09-25"));
        Actor brie = new Actor("Brie Larson", LocalDate.parse("1989-10-01"));
        Actor denzel = new Actor("Denzel Washington", LocalDate.parse("1954-12-28"));

        List<Actor> actors = List.of(
                leonardo, tomHardy, ellenPage, meryl, jennifer, emma,
                samuel, hugh, natalie, margot, ryan, gal, will, brie, denzel
        );

        actorRepository.saveAll(actors);

        // --- Movies ---
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie("Die Hard", "1988", "132", List.of(action), new ArrayList<>()));
        movies.add(new Movie("The Matrix", "1999", "136", List.of(action, sciFi), new ArrayList<>()));
        movies.add(new Movie("Mission Impossible", "1996", "110", List.of(action), new ArrayList<>()));

        movies.add(new Movie("Inception", "2010", "148", List.of(action, sciFi, thriller),
                List.of(leonardo, tomHardy, ellenPage)));
        movies.add(new Movie("The Devil Wears Prada", "2006", "109", List.of(drama, comedy),
                List.of(meryl)));
        movies.add(new Movie("Mamma Mia!", "2008", "108", List.of(comedy, drama),
                List.of(meryl)));
        movies.add(new Movie("The Iron Lady", "2011", "105", List.of(drama),
                List.of(meryl)));
        movies.add(new Movie("La La Land", "2016", "128", List.of(comedy, drama),
                List.of(emma, ryan)));
        movies.add(new Movie("Avengers: Endgame", "2019", "181", List.of(action, sciFi),
                new ArrayList<>()));
        movies.add(new Movie("Wonder Woman", "2017", "141", List.of(action, sciFi),
                List.of(gal)));
        movies.add(new Movie("Silver Linings Playbook", "2012", "122", List.of(comedy, drama),
                List.of(jennifer, brie)));
        movies.add(new Movie("Hidden Figures", "2016", "127", List.of(drama, sciFi),
                new ArrayList<>()));
        movies.add(new Movie("Mad Max: Fury Road", "2015", "120", List.of(action, sciFi),
                List.of(tomHardy)));
        movies.add(new Movie("Joker", "2019", "122", List.of(drama, thriller),
                new ArrayList<>()));
        movies.add(new Movie("Titanic", "1997", "195", List.of(drama),
                List.of(leonardo)));

        movieRepository.saveAll(movies);

        System.out.println("✅ Sample Database seeded successfully!");
    }
}
