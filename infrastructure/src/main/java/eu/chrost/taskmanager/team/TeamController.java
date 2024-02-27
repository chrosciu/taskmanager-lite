package eu.chrost.taskmanager.team;


import eu.chrost.taskmanager.commons.dto.TeamMembersDto;
import eu.chrost.taskmanager.team.dto.TeamFullDto;
import eu.chrost.taskmanager.team.dto.TeamShortDto;
import eu.chrost.taskmanager.team.dto.TeamDto;
import eu.chrost.taskmanager.team.exception.TeamAlreadyExistsException;
import eu.chrost.taskmanager.team.exception.TeamNotFoundException;
import eu.chrost.taskmanager.user.UserFacade;
import eu.chrost.taskmanager.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
class TeamController {
    private final TeamFacade teamFacade;
    private final TeamQueryRepository teamQueryRepository;
    private final UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<TeamShortDto>> findAll() {
        return new ResponseEntity<>(teamQueryRepository.findAllBy(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamFullDto> findById(@PathVariable("id") long id) {
        return teamQueryRepository.findDtoById(id)
                .map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> createTeam(@RequestBody TeamDto teamDto, UriComponentsBuilder uriComponentsBuilder) {
        try {
            long createdTeamId = teamFacade.createTeamAndReturnItsId(teamDto);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/teams/{id}").buildAndExpand(createdTeamId).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (TeamAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TeamFullDto> updateTeam(@PathVariable("id") long id, @RequestBody TeamDto teamDto) {
        try {
            teamFacade.updateTeamWithId(id, teamDto);
            TeamFullDto updatedTeam = teamQueryRepository.findDtoById(id).get();
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        } catch (TeamNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/members")
    @Transactional
    public ResponseEntity<Void> addTeamMembers(@PathVariable("id") long id, @RequestBody TeamMembersDto dto) {
        try {
            teamFacade.addMembersToTeam(id, dto);
            userFacade.addTeamToUsersTeams(dto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TeamNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/members")
    @Transactional
    public ResponseEntity<Void> removeTeamMembers(@PathVariable("id") long id, @RequestBody TeamMembersDto dto) {
        try {
            teamFacade.removeMembersFromTeam(id, dto);
            userFacade.removeTeamFromUsersTeams(dto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TeamNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") long id) {
        try {
            teamFacade.deleteTeamWithId(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TeamNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
