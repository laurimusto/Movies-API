package com.lauri.kood.movieapi.dto;

import java.time.LocalDate;

public record ActorPostDTO(
        String name,
        LocalDate birthdate)
{
}
