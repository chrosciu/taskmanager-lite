package eu.chrost.taskmanager.team;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface TeamRepository extends Repository<Team, Long> {

    Team save(Team team);

    void delete(Team team);

    Optional<Team> findById(long id);
}
