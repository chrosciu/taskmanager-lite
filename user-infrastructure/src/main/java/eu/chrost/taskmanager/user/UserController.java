package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserDto;
import eu.chrost.taskmanager.user.dto.UserFullDto;
import eu.chrost.taskmanager.user.dto.UserShortDto;
import eu.chrost.taskmanager.user.exception.UserAlreadyExistsException;
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
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;
    private final UserQueryRepository userQueryRepository;

    @GetMapping
    public ResponseEntity<List<UserShortDto>> getAllUsers() {
        return new ResponseEntity<>(userQueryRepository.findBy(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserFullDto> getUser(@PathVariable("id") long id) {
        return userQueryRepository.findDtoById(id)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto, UriComponentsBuilder uriComponentsBuilder) {
        try {
            long createdUserId = userFacade.createNewUserAndReturnItsId(userDto);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/users/{id}").buildAndExpand(createdUserId).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<UserShortDto> updateUser(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        try {
            userFacade.updateUserWithId(id, userDto);
            UserShortDto updatedUserDto = userQueryRepository.findDtoById(id).get();
            return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        try {
            userFacade.deleteUserWithId(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
