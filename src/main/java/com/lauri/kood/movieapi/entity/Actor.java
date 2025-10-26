package com.lauri.kood.movieapi.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) //makes name field not nullable(cant be left empty)
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    public Actor(String name, String birthdate) { //constructor for string
        this.name = name;
        this.birthdate = LocalDate.parse(birthdate);
    }

    public Actor(String name, LocalDate birthdate) { //Constructor for localdate
        this.name = name;
        this.birthdate = birthdate;
    }

    public Actor() {

    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", id=" + id +
                '}';
    }

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

}