package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.team.dto.TeamDto;
import eu.chrost.taskmanager.team.dto.TeamMembersDto;
import eu.chrost.taskmanager.team.exception.TeamAlreadyExistsException;
import eu.chrost.taskmanager.team.exception.TeamNotFoundException;
import eu.chrost.taskmanager.user.dto.SimpleUserQueryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamFacade {
    private final TeamRepository teamRepository;
    private final TeamQueryRepository teamQueryRepository;

    public long createTeamAndReturnItsId(TeamDto teamDto) throws TeamAlreadyExistsException {
        if (teamQueryRepository.existsByName(teamDto.getName())) {
            throw new TeamAlreadyExistsException();
        } else {
            Team team = new Team();
            team.setName(teamDto.getName());
            Team saved = teamRepository.save(team);
            return saved.getId();
        }
    }

    public void deleteTeamWithId(long id) throws TeamNotFoundException {
        Team team = getTeamById(id);
        teamRepository.delete(team);
    }

    public void updateTeamWithId(long id, TeamDto teamDto) throws TeamNotFoundException {
        Team team = getTeamById(id);

        if (teamDto.getName() != null) {
            team.setName(teamDto.getName());
        }

        if (teamDto.getCodenameShort() != null && teamDto.getCodenameFull() != null) {
            Codename codename = new Codename();
            codename.setShortName(teamDto.getCodenameShort());
            codename.setFullName(teamDto.getCodenameFull());
            team.setCodename(codename);
        }

        if (teamDto.getDescription() != null) {
            team.setDescription(teamDto.getDescription());
        }

        teamRepository.save(team);
    }

    public void addMembersToTeam(long teamId, TeamMembersDto teamMembersDto) throws TeamNotFoundException {
        Team team = getTeamById(teamId);
        for (long userId : teamMembersDto.getUserIds()) {
            SimpleUserQueryEntity user = new SimpleUserQueryEntity(userId);
            team.addMember(user);
        }
        teamRepository.save(team);
    }

    public void removeMembersFromTeam(long teamId, TeamMembersDto teamMembersDto) throws TeamNotFoundException {
        Team team = getTeamById(teamId);
        for (long userId : teamMembersDto.getUserIds()) {
            SimpleUserQueryEntity user = new SimpleUserQueryEntity(userId);
            team.removeMember(user);
        }
        teamRepository.save(team);
    }

    private Team getTeamById(long id) {
        Optional<Team> team = teamRepository.findById(id);

        if (team.isEmpty()) {
            throw new TeamNotFoundException();
        }

        return team.get();
    }
}
