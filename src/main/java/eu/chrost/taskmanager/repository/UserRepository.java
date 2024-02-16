package eu.chrost.taskmanager.repository;

import eu.chrost.taskmanager.model.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserNameFirstNameAndUserNameLastName(String firstName, String lastName);
}
