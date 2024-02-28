package eu.chrost.taskmanager.team.domain;

import java.util.Optional;

public interface TeamRepository {
    Team save(Team team);

    void delete(Team team);

    Optional<Team> findById(long id);
}
