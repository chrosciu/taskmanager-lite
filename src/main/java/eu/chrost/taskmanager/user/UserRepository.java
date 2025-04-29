package eu.chrost.taskmanager.user;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends Repository<User, Long> {

    //COMMANDS

    User save(User user);

    void delete(User user);

    Optional<User> findById(long id);

    //QUERIES

    List<User> findAll();

    boolean existsByUserNameFirstNameAndUserNameLastName(String firstName, String lastName);
}
