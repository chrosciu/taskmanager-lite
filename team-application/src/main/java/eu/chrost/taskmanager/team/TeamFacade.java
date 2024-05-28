package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.team.dto.TeamMembersDto;
import eu.chrost.taskmanager.common.event.EventPublisher;
import eu.chrost.taskmanager.common.event.TeamMembersEvent;
import eu.chrost.taskmanager.team.dto.TeamDto;
import eu.chrost.taskmanager.team.exception.TeamAlreadyExistsException;
import eu.chrost.taskmanager.team.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamFacade {
    private final TeamRepository teamRepository;
    private final TeamQueryRepository teamQueryRepository;
    private final EventPublisher eventPublisher;

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
            SimpleUser user = new SimpleUser(userId);
            team.addMember(user);
        }
        teamRepository.save(team);
        TeamMembersEvent teamMembersEvent =
                new TeamMembersEvent(TeamMembersEvent.Type.MEMBERS_ADDED, teamId, teamMembersDto.getUserIds());
        eventPublisher.publish(teamMembersEvent);
    }

    public void removeMembersFromTeam(long teamId, TeamMembersDto teamMembersDto) throws TeamNotFoundException {
        Team team = getTeamById(teamId);
        for (long userId : teamMembersDto.getUserIds()) {
            SimpleUser user = new SimpleUser(userId);
            team.removeMember(user);
        }
        teamRepository.save(team);
        TeamMembersEvent teamMembersEvent =
                new TeamMembersEvent(TeamMembersEvent.Type.MEMBERS_REMOVED, teamId, teamMembersDto.getUserIds());
        eventPublisher.publish(teamMembersEvent);
    }

    private Team getTeamById(long id) {
        return teamRepository.findById(id)
                .orElseThrow(TeamNotFoundException::new);
    }
}
