package eu.chrost.taskmanager.user;

import java.util.Optional;

interface UserRepository {
    User save(User user);

    void delete(User user);

    Optional<User> findById(long id);
}
