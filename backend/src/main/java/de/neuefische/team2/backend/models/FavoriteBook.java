package de.neuefische.team2.backend.models;

import lombok.With;
import org.springframework.data.annotation.Id;

@With
public record FavoriteBook(

        @Id
        String id,
        Book book
) {
}
