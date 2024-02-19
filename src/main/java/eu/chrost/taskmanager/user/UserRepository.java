package eu.chrost.taskmanager.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserNameFirstNameAndUserNameLastName(String firstName, String lastName);
}
