package de.neuefische.team2.backend.repos;

import de.neuefische.team2.backend.models.Book;
import de.neuefische.team2.backend.models.FavoriteBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteBooksRepo extends MongoRepository<FavoriteBook, String> {
    Optional<FavoriteBook> findByBookId(String s);
}
