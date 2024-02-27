package eu.chrost.taskmanager.team;

import java.util.Optional;

interface TeamRepository {
    Team save(Team team);

    void delete(Team team);

    Optional<Team> findById(long id);
}
