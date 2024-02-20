package eu.chrost.taskmanager.user;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends Repository<User, Long> {

    User save(User user);

    void delete(User user);

    Optional<User> findById(long id);

    List<User> findAll();

    boolean existsByUserNameFirstNameAndUserNameLastName(String firstName, String lastName);
}
