package de.neuefische.team2.backend.models;

import lombok.With;

public record BookDto(

        String title,
        String author,
        String genre,
        Integer year,
        String publisher,
        String city,
        Integer page,
        String description,
        Integer views
        )
{
}
