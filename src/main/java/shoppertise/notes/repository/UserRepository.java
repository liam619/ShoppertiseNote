package shoppertise.notes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import shoppertise.notes.model.UserDetail;

public interface UserRepository extends MongoRepository<UserDetail, String> {

    UserDetail findByUsername(String username);
}
