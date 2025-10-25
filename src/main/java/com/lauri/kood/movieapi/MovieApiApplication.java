package com.lauri.kood.movieapi;

import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.repository.ActorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class MovieApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ActorRepository repository) { //returns
        return (args -> {
//            insertJavaAdvocates(repository);
//            System.out.println(repository.findAll());
//            System.out.println("DB absolute path: " + new java.io.File("movie.db").getAbsolutePath());
            System.out.println(repository.findActorByName("Lauri"));
            System.out.println();

        });
    }

    private void insertJavaAdvocates(ActorRepository repository) {
        repository.save(new Actor("Lauri", "1995-09-14"));
        repository.save(new Actor("Tere", "1992-12-11"));
        repository.save(new Actor("jou", "1925-02-14"));
        repository.save(new Actor("Simba tere", "1295-05-14"));
    }
}