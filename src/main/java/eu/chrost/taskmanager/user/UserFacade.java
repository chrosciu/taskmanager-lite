package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.team.Team;
import eu.chrost.taskmanager.user.dto.UserDto;
import eu.chrost.taskmanager.user.exception.UserAlreadyExistsException;
import eu.chrost.taskmanager.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        List<UserDto> usersDtos = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getUserName().getFirstName());
            userDto.setLastName(user.getUserName().getLastName());
            userDto.setLogin(user.getLogin());
            userDto.setPassword(user.getPassword());

            UserRole userRole = user.getUserRole();
            if (userRole != null) {
                userDto.setUserRole(userRole.name());
            }

            usersDtos.add(userDto);
        }
        return usersDtos;
    }

    public UserDto getUserWithId(long id) throws UserNotFoundException {
        User user = getUserById(id);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getUserName().getFirstName());
        userDto.setLastName(user.getUserName().getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setTeamIds(user.getTeams().stream().map(Team::getId).collect(toList()));

        UserRole userRole = user.getUserRole();
        if (userRole != null) {
            userDto.setUserRole(userRole.name());
        }
        return userDto;
    }

    public long createNewUserAndReturnItsId(UserDto userDto) throws UserAlreadyExistsException {
        if (exists(userDto)) {
            throw new UserAlreadyExistsException();
        } else {
            User user = new User();
            user.setUserRole(UserRole.valueOf(userDto.getUserRole()));
            UserName userName = new UserName();
            userName.setFirstName(userDto.getFirstName());
            userName.setLastName(userDto.getLastName());
            user.setUserName(userName);
            user.setLogin(userDto.getLogin());
            user.setPassword(userDto.getPassword());

            User saved = userRepository.save(user);

            return saved.getId();
        }
    }

    public void updateUserWithId(long id, UserDto userDto) throws UserNotFoundException {
        User user = getUserById(id);

        if (userDto.getLogin() != null) {
            user.setLogin(userDto.getLogin());
        }

        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }

        if (userDto.getUserRole() != null) {
            user.setUserRole(UserRole.valueOf(userDto.getUserRole()));
        }

        userRepository.save(user);
    }

    public void deleteUserWithId(long id) throws UserNotFoundException {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    private User getUserById(Long id) {
        Optional<User> user;
        user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return user.get();
    }

    private boolean exists(UserDto userDto) {
        return !userRepository.findByUserNameFirstNameAndUserNameLastName(userDto.getFirstName(), userDto.getLastName()).isEmpty();
    }
}