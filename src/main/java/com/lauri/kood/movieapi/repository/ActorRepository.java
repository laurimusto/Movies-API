package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findByNameContainingIgnoreCase(String name);

    List<Actor> Id(Long id);
    //List<Actor> findActorByName(String name);
    //List<Actor> findActorByBirthdate(LocalDate birthdate);
}
