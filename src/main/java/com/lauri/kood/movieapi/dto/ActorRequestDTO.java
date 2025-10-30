package com.lauri.kood.movieapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ActorRequestDTO(
        String name,

        LocalDate birthdate)
{
}
