package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.team.dto.TeamDto;
import eu.chrost.taskmanager.team.dto.TeamMembersDto;
import eu.chrost.taskmanager.team.exception.TeamAlreadyExistsException;
import eu.chrost.taskmanager.team.exception.TeamNotFoundException;
import eu.chrost.taskmanager.user.dto.SimpleUserQueryEntity;
import eu.chrost.taskmanager.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TeamFacade {
    private final TeamRepository teamRepository;

    public List<TeamDto> getAllTeams() {
        List<TeamDto> teams = StreamSupport.stream(teamRepository.findAll().spliterator(), false)
                .map(team -> {
                    TeamDto dto = new TeamDto();
                    dto.setId(team.getId());
                    dto.setName(team.getName());

                    if (team.getCodename() != null) {
                        dto.setCodenameShort(team.getCodename().getShortName());
                        dto.setCodenameFull(team.getCodename().getFullName());
                    }

                    dto.setDescription(team.getDescription());

                    return dto;
                })
                .collect(toList());
        return teams;
    }

    public TeamDto getTeamWithId(long id) throws TeamNotFoundException {
        Team team = getTeamById(id);
        TeamDto dto = new TeamDto();
        dto.setId(team.getId());
        dto.setName(team.getName());

        if (team.getCodename() != null) {
            dto.setCodenameShort(team.getCodename().getShortName());
            dto.setCodenameFull(team.getCodename().getFullName());
        }

        dto.setDescription(team.getDescription());
        dto.setUserIds(team.getMembers().stream().map(SimpleUserQueryEntity::getId).collect(toList()));

        return dto;
    }

    public long createTeamAndReturnItsId(TeamDto teamDto) throws TeamAlreadyExistsException {
        if (teamRepository.existsByName(teamDto.getName())) {
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

    public void addMembersToTeam(long teamId, TeamMembersDto teamMembersDto) throws TeamNotFoundException, UserNotFoundException {
        Team team = getTeamById(teamId);
        for (long userId : teamMembersDto.getUserIds()) {
            SimpleUserQueryEntity user = new SimpleUserQueryEntity(userId);
            team.addMember(user);
        }
        teamRepository.save(team);
    }

    public void removeMembersFromTeam(long teamId, TeamMembersDto teamMembersDto) throws TeamNotFoundException, UserNotFoundException {
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
