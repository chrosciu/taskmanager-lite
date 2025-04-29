package eu.chrost.taskmanager.team;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface TeamRepository extends CrudRepository<Team, Long> {
    Optional<Team> findByName(String name);
}
