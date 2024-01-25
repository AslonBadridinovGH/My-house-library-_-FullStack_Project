package de.neuefische.team2.backend.models;
import org.springframework.data.annotation.Id;

public record Book(
        @Id
        String id,
        String title,
        String author,
        String genre,
        Integer year,
        String publisher,
        String city,
        Integer page,
        String description,
        Integer views
) {}

