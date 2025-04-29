package eu.chrost.taskmanager.team;


import eu.chrost.taskmanager.team.dto.TeamDto;
import eu.chrost.taskmanager.team.dto.TeamMembersDto;
import eu.chrost.taskmanager.team.exception.TeamAlreadyExistsException;
import eu.chrost.taskmanager.team.exception.TeamNotFoundException;
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

    @GetMapping
    public ResponseEntity<List<TeamDto>> findAll() {
        List<TeamDto> teams = teamFacade.getAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<TeamDto> findById(@PathVariable long id) {
        try {
            TeamDto team = teamFacade.getTeamWithId(id);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (TeamNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
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
    public ResponseEntity<TeamDto> updateTeam(@PathVariable long id, @RequestBody TeamDto teamDto) {
        try {
            teamFacade.updateTeamWithId(id, teamDto);
            TeamDto updatedTeam = teamFacade.getTeamWithId(id);
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        } catch (TeamNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/members")
    @Transactional
    public ResponseEntity<Void> addTeamMembers(@PathVariable Long id, @RequestBody TeamMembersDto dto) {
        try {
            teamFacade.addMembersToTeamWithId(id, dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TeamNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/members")
    @Transactional
    public ResponseEntity<Void> removeTeamMembers(@PathVariable Long id, @RequestBody TeamMembersDto dto) {
        try {
            teamFacade.removeMembersFromTeamWithId(id ,dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TeamNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable long id) {
        try {
            teamFacade.deleteTeamWithId(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TeamNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
