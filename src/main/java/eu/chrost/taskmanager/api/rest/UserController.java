package eu.chrost.taskmanager.api.rest;

import eu.chrost.taskmanager.dto.UserDto;
import eu.chrost.taskmanager.exception.UserNotFoundException;
import eu.chrost.taskmanager.model.embedded.UserName;
import eu.chrost.taskmanager.model.entities.User;
import eu.chrost.taskmanager.model.enums.TeamRole;
import eu.chrost.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersDtos = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getUserName().getFirstName());
            userDto.setLastName(user.getUserName().getLastName());
            userDto.setLogin(user.getLogin());
            userDto.setPassword(user.getPassword());

            TeamRole teamRole = user.getTeamRole();
            if (teamRole != null) {
                userDto.setTeamRole(teamRole.name());
            }

            usersDtos.add(userDto);
        }

        return new ResponseEntity<>(usersDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        try {
            User user = getUserById(id);

            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getUserName().getFirstName());
            userDto.setLastName(user.getUserName().getLastName());
            userDto.setLogin(user.getLogin());
            userDto.setPassword(user.getPassword());

            TeamRole teamRole = user.getTeamRole();
            if (teamRole != null) {
                userDto.setTeamRole(teamRole.name());
            }

            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto, UriComponentsBuilder uriComponentsBuilder) {
        if (exists(userDto)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            User user = new User();
            user.setTeamRole(TeamRole.valueOf(userDto.getTeamRole()));
            UserName userName = new UserName();
            userName.setFirstName(userDto.getFirstName());
            userName.setLastName(userDto.getLastName());
            user.setUserName(userName);
            user.setLogin(userDto.getLogin());
            user.setPassword(userDto.getPassword());

            User saved = userRepository.save(user);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/users/{id}").buildAndExpand(saved.getId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    private boolean exists(UserDto userDto) {
        return !userRepository.findByUserNameFirstNameAndUserNameLastName(userDto.getFirstName(), userDto.getLastName()).isEmpty();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        User user;

        try {
            user = getUserById(id);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (userDto.getLogin() != null) {
            user.setLogin(userDto.getLogin());
        }

        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }

        if (userDto.getTeamRole() != null) {
            user.setTeamRole(TeamRole.valueOf(userDto.getTeamRole()));
        }
        
        User updated = userRepository.save(user);

        UserDto response = new UserDto();
        response.setId(updated.getId());
        response.setFirstName(updated.getUserName().getFirstName());
        response.setLastName(updated.getUserName().getLastName());
        response.setLogin(updated.getLogin());
        response.setPassword(updated.getPassword());

        TeamRole teamRole = updated.getTeamRole();
        if (teamRole != null) {
            response.setTeamRole(teamRole.name());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        User user;

        try {
            user = getUserById(id);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getUserById(Long id) {
        Optional<User> user;
        user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return user.get();
    }
}
