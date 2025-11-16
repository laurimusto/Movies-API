package com.lauri.kood.movieapi.mapper;

import com.lauri.kood.movieapi.dto.ActorResponseDTO;
import com.lauri.kood.movieapi.entity.Actor;

public class ActorMapper {
    public static ActorResponseDTO toActorResponseDto(Actor actor) {
        return new ActorResponseDTO(actor.getId(),
                actor.getName(),
                actor.getBirthdate()
        );
    }
}
