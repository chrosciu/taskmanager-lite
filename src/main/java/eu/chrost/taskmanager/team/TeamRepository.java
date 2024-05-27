package eu.chrost.taskmanager.team;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface TeamRepository extends Repository<Team, Long> {

    //COMMANDS

    Team save(Team team);

    void delete(Team team);

    Optional<Team> findById(long id);

    //QUERIES

    List<Team> findAll();

    boolean existsByName(String name);
}
