package eu.chrost.taskmanager.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserRepository extends Repository<User, Long> {

    User save(User user);

    void delete(User user);

    Optional<User> findById(long id);
}
