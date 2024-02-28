package eu.chrost.taskmanager.team.infrastructure;

import eu.chrost.taskmanager.team.application.TeamQueryRepository;
import eu.chrost.taskmanager.team.application.dto.TeamFullDto;
import eu.chrost.taskmanager.team.application.dto.TeamShortDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface JpaTeamQueryRepository extends Repository<JpaTeam, Long>, TeamQueryRepository {
    <T> List<T> findAllBy(Class<T> type);

    default List<TeamShortDto> findAllBy() {
        return findAllBy(JpaTeamShortDto.class).stream().map(t -> (TeamShortDto)t).toList();
    }

    <T> Optional<T> findDtoById(long id, Class<T> type);

    @Override
    default Optional<TeamFullDto> findDtoById(long id) {
        return findDtoById(id, JpaTeamFullDto.class).map(t -> t);
    }
}
