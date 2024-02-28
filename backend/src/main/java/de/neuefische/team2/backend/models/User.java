package de.neuefische.team2.backend.models;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record User(
        @Id
        String id,
        String name,
        @With
        List<String> favorites
) {
    public User(Map<String, Object> attributes) {
        this(attributes.get("id").toString(), attributes.get("login").toString(), new ArrayList<>());
    }
}
