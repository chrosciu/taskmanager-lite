package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.team.dto.TeamDto;
import eu.chrost.taskmanager.team.dto.TeamMembersDto;
import eu.chrost.taskmanager.team.exception.TeamAlreadyExistsException;
import eu.chrost.taskmanager.team.exception.TeamNotFoundException;
import eu.chrost.taskmanager.user.User;
import eu.chrost.taskmanager.user.UserRepository;
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
    private final UserRepository userRepository;

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
        dto.setUserIds(team.getMembers().stream().map(User::getId).collect(toList()));

        return dto;
    }

    public long createTeamAndReturnItsId(TeamDto teamDto) throws TeamAlreadyExistsException {
        if (teamRepository.findByName(teamDto.getName()).isPresent()) {
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

    public void addMembersToTeamWithId(long id, TeamMembersDto teamMembersDto) throws TeamNotFoundException, UserNotFoundException {
        Team team = getTeamById(id);
        List<User> users = findUsers(teamMembersDto);

        if (users.size() != teamMembersDto.getUserIds().size()) {
            throw new UserNotFoundException();
        }

        users.forEach(user -> {
            user.addToTeam(team);
            team.addMember(user);
        });

        teamRepository.save(team);
        userRepository.saveAll(users);
    }

    public void removeMembersFromTeamWithId(long id, TeamMembersDto teamMembersDto) throws TeamNotFoundException {
        Team team = getTeamById(id);
        Iterable<User> users = findUsers(teamMembersDto);

        users.forEach(user -> {
            if (user.getTeams().contains(team)) {
                user.removeFrom(team);
                team.removeMember(user);
            }
        });

        teamRepository.save(team);
        userRepository.saveAll(users);
    }

    private Team getTeamById(long id) {
        Optional<Team> team = teamRepository.findById(id);

        if (team.isEmpty()) {
            throw new TeamNotFoundException();
        }

        return team.get();
    }

    private List<User> findUsers(TeamMembersDto dto) {
        return StreamSupport.stream(userRepository.findAllById(dto.getUserIds()).spliterator(), false).toList();
    }
}
