package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.team.dto.TeamFullDto;
import eu.chrost.taskmanager.team.dto.TeamShortDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TeamQueryRepository {
    List<TeamShortDto> findAllBy();

    Optional<TeamFullDto> findDtoById(long id);

    boolean existsByName(String name);
}
