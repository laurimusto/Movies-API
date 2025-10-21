package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long> {
}
