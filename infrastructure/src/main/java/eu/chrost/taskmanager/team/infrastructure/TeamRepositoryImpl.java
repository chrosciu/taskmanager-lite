package eu.chrost.taskmanager.team.infrastructure;

import eu.chrost.taskmanager.team.domain.Team;
import eu.chrost.taskmanager.team.domain.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
class TeamRepositoryImpl implements TeamRepository {
    private final JpaTeamRepository teamRepository;

    @Override
    public Team save(Team team) {
        return teamRepository.save(JpaTeam.fromTeam(team)).toTeam();
    }

    @Override
    public void delete(Team team) {
        teamRepository.delete(JpaTeam.fromTeam(team));
    }

    @Override
    public Optional<Team> findById(long id) {
        return teamRepository.findById(id).map(JpaTeam::toTeam);
    }
}
