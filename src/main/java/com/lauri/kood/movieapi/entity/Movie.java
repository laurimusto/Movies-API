package com.lauri.kood.movieapi.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) //makes name field not nullable(cant be left empty)
    private String title;
    private String releaseYear;
    private String duration;

    public Movie(String title, String releaseYear, String duration) { //Constructor for everything else.
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
    }

    public Movie(String title, String releaseYear, String duration, //Constructor for seeding the database.
                 List<Genre> genres, List<Actor> actors) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.genres = genres;
        this.actors = actors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Movie() {
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }




    @ManyToMany
    @JoinTable(
            name = "movie_actor",           // ← Join table name
            joinColumns = @JoinColumn(name = "movie_id"),         // ← This entity's FK
            inverseJoinColumns = @JoinColumn(name = "actor_id")   // ← Other entity's FK
    )
    private List<Actor> actors;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
