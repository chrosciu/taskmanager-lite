package eu.chrost.taskmanager.team.internal;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TeamQueryRepository extends Repository<Team, Long> {
    List<TeamShortDto> findAllBy();

    Optional<TeamFullDto> findDtoById(long id);

    boolean existsByName(String name);
}
