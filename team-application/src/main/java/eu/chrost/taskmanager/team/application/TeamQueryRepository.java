package eu.chrost.taskmanager.team.application;

import eu.chrost.taskmanager.team.application.dto.TeamFullDto;
import eu.chrost.taskmanager.team.application.dto.TeamShortDto;

import java.util.List;
import java.util.Optional;

public interface TeamQueryRepository {
    List<TeamShortDto> findAllBy();

    Optional<TeamFullDto> findDtoById(long id);

    boolean existsByName(String name);
}
