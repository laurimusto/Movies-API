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
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        if (movieRepository.count() > 0) return;

        // --- Genres ---
        Genre action = new Genre("Action");
        Genre comedy = new Genre("Comedy");
        Genre drama = new Genre("Drama");
        Genre sciFi = new Genre("Sci-Fi");
        Genre thriller = new Genre("Thriller");
        Genre crime = new Genre("Crime");
        Genre adventure = new Genre("Adventure");
        Genre romance = new Genre("Romance");

        genreRepository.saveAll(Set.of(action, comedy, drama, sciFi, thriller, crime, adventure, romance));
        System.out.println("✅ Sample genres loaded!");

        // --- Actors ---
        Actor tomHanks = new Actor("Tom Hanks", LocalDate.parse("1955-12-21"));
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
        Actor keanu = new Actor("Keanu Reeves", LocalDate.parse("1964-09-02"));
        Actor robertDowneyJr = new Actor("Robert Downey Jr.", LocalDate.parse("1965-04-04"));
        Actor chrisEvans = new Actor("Chris Evans", LocalDate.parse("1981-06-13"));
        Actor scarlettJohansson = new Actor("Scarlett Johansson", LocalDate.parse("1984-11-22"));
        Actor bradPitt = new Actor("Brad Pitt", LocalDate.parse("1963-12-18"));
        Actor edwardNorton = new Actor("Edward Norton", LocalDate.parse("1969-08-18"));
        Actor helenaBonhamCarter = new Actor("Helena Bonham Carter", LocalDate.parse("1966-05-26"));
        Actor samWorthington = new Actor("Sam Worthington", LocalDate.parse("1976-08-02"));
        Actor zoeSaldana = new Actor("Zoe Saldana", LocalDate.parse("1978-06-19"));
        Actor sigourneyWeaver = new Actor("Sigourney Weaver", LocalDate.parse("1949-10-08"));
        Actor matthewMcConaughey = new Actor("Matthew McConaughey", LocalDate.parse("1969-11-04"));
        Actor anneHathaway = new Actor("Anne Hathaway", LocalDate.parse("1982-11-12"));
        Actor jessicaChastain = new Actor("Jessica Chastain", LocalDate.parse("1977-03-24"));
        Actor marlonBrando = new Actor("Marlon Brando", LocalDate.parse("1924-04-03"));
        Actor alPacino = new Actor("Al Pacino", LocalDate.parse("1940-04-25"));
        Actor jamesCaan = new Actor("James Caan", LocalDate.parse("1940-03-26"));
        Actor johnTravolta = new Actor("John Travolta", LocalDate.parse("1954-02-18"));
        Actor umaThurman = new Actor("Uma Thurman", LocalDate.parse("1970-04-29"));
        Actor christianBale = new Actor("Christian Bale", LocalDate.parse("1974-01-30"));
        Actor heathLedger = new Actor("Heath Ledger", LocalDate.parse("1979-04-04"));
        Actor aaronEckhart = new Actor("Aaron Eckhart", LocalDate.parse("1968-03-12"));
        Actor robinWright = new Actor("Robin Wright", LocalDate.parse("1966-04-08"));
        Actor garySinise = new Actor("Gary Sinise", LocalDate.parse("1955-03-17"));
        Actor samuelLJackson = new Actor("samuel L Jackson", LocalDate.parse("1955-03-17"));



        actorRepository.saveAll(Set.of(
                tomHanks, leonardo, tomHardy, ellenPage, meryl, jennifer, emma,
                samuel, hugh, natalie, margot, ryan, gal, will, brie, denzel, keanu,
                robertDowneyJr, chrisEvans, scarlettJohansson, bradPitt, edwardNorton,
                helenaBonhamCarter, samWorthington, zoeSaldana, sigourneyWeaver,
                matthewMcConaughey, anneHathaway, jessicaChastain,
                marlonBrando, alPacino, jamesCaan, johnTravolta, umaThurman,
                christianBale, heathLedger, aaronEckhart, robinWright, garySinise, samuelLJackson
        ));
        System.out.println("✅ Sample actors loaded!");



        // --- Movies ---
        Movie dieHard = new Movie("Die Hard", 1988, 132,
                Set.of(action), Set.of(tomHanks));
        Movie theMatrix = new Movie("The Matrix", 1999, Integer.parseInt("136"),
                Set.of(action, sciFi), Set.of(keanu));
        Movie inception = new Movie("Inception", 2010, Integer.parseInt("148"),
                Set.of(action, sciFi, thriller), Set.of(leonardo, tomHardy, ellenPage));
        Movie interstellar = new Movie("Interstellar", 2014, Integer.parseInt("169"),
                Set.of(sciFi, drama, adventure), Set.of(matthewMcConaughey, anneHathaway, jessicaChastain));
        Movie theGodfather = new Movie("The Godfather", 1972, Integer.parseInt("175"),
                Set.of(drama, crime), Set.of(marlonBrando, alPacino, jamesCaan));
        Movie pulpFiction = new Movie("Pulp Fiction", 1994, Integer.parseInt("154"),
                Set.of(crime, drama, thriller), Set.of(johnTravolta, samuelLJackson, umaThurman));
        Movie theDarkKnight = new Movie("The Dark Knight", 2008, Integer.parseInt("152"),
                Set.of(action, thriller, drama), Set.of(christianBale, heathLedger, aaronEckhart));
        Movie forrestGump = new Movie("Forrest Gump", 1994, Integer.parseInt("142"),
                Set.of(drama, romance), Set.of(tomHanks, robinWright, garySinise));
        Movie theAvengers = new Movie("The Avengers", 2012, Integer.parseInt("143"),
                Set.of(action, adventure, sciFi), Set.of(robertDowneyJr, chrisEvans, scarlettJohansson));
        Movie fightClub = new Movie("Fight Club", 1999, Integer.parseInt("139"),
                Set.of(drama, thriller), Set.of(bradPitt, edwardNorton, helenaBonhamCarter));
        Movie avatar = new Movie("Avatar", 2009, Integer.parseInt("162"),
                Set.of(action, adventure, sciFi), Set.of(samWorthington, zoeSaldana, sigourneyWeaver));

        movieRepository.saveAll(Set.of(dieHard, theMatrix, inception, interstellar, theGodfather, pulpFiction, theDarkKnight, forrestGump, theAvengers, fightClub, avatar));
        System.out.println("✅ Sample Movies loaded!");
    }
}