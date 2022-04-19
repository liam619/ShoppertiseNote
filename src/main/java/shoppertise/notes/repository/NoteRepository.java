package shoppertise.notes.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import shoppertise.notes.model.Note;

public interface NoteRepository extends MongoRepository<Note, String> {
    
    Optional<Note> findByTitle(String title);
}
