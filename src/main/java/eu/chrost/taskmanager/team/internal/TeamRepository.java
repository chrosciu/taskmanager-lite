package eu.chrost.taskmanager.team.internal;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface TeamRepository extends Repository<Team, Long> {

    Team save(Team team);

    void delete(Team team);

    Optional<Team> findById(long id);
}
