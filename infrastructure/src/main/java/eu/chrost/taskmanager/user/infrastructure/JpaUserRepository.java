package eu.chrost.taskmanager.user.infrastructure;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface JpaUserRepository extends Repository<JpaUser, Long> {
    JpaUser save(JpaUser user);

    void delete(JpaUser user);

    Optional<JpaUser> findById(long id);
}
