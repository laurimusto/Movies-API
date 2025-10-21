package com.lauri.kood.movieapi;

import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.repository.ActorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class MovieApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieApiApplication.class, args);
    }

    private void insertJavaAdvocates(ActorRepository repository) {
        repository.save(new Actor("Lauri", "1995-09-14"));
        repository.save(new Actor("Tere", "1992-12-11"));
        repository.save(new Actor("jou", "1925-02-14"));
        repository.save(new Actor("Simba tere", "1295-05-14"));
    }
}
