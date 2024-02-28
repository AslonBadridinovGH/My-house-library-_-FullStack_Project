package de.neuefische.team2.backend.repos;

import de.neuefische.team2.backend.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends MongoRepository<Message, String> {
}
