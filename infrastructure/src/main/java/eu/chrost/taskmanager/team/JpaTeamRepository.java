package eu.chrost.taskmanager.team;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface JpaTeamRepository extends Repository<JpaTeam, Long> {
    JpaTeam save(JpaTeam team);

    void delete(JpaTeam team);

    Optional<JpaTeam> findById(long id);
}
