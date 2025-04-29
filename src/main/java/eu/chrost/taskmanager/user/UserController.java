package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserDto;
import eu.chrost.taskmanager.user.exception.UserAlreadyExistsException;
import eu.chrost.taskmanager.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersDtos = userFacade.getAllUsers();
        return new ResponseEntity<>(usersDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        try {
            UserDto userDto = userFacade.getUserWithId(id);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
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
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        try {
            userFacade.updateUserWithId(id, userDto);
            UserDto updatedUserDto = userFacade.getUserWithId(id);
            return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        try {
            userFacade.deleteUserWithId(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
