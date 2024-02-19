package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.team.query.SimpleTeamQueryDto;
import eu.chrost.taskmanager.user.dto.UserDto;
import eu.chrost.taskmanager.user.exception.UserAlreadyExistsException;
import eu.chrost.taskmanager.user.exception.UserNotFoundException;
import eu.chrost.taskmanager.user.query.SimpleUserQueryDto;
import eu.chrost.taskmanager.user.query.SimpleUserQueryDtoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserRepository userRepository;
    private final SimpleUserQueryDtoRepository simpleUserQueryDtoRepository;

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

    public UserDto getUserWithId(long id) {
        User user = getUserById(id);

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

        return userDto;
    }

    public SimpleUserQueryDto getSimpleUserWithId(long id) {
        return simpleUserQueryDtoRepository.findById(id).orElseThrow(UserNotFoundException::new);
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

    public void addUserWithIdToGivenTeam(long id, SimpleTeamQueryDto team) {
        User user = getUserById(id);
        user.addToTeam(team);
        userRepository.save(user);
    }

    public void removeUserWithIdFromGivenTeam(long id, SimpleTeamQueryDto team) {
        User user = getUserById(id);
        user.removeFrom(team);
        userRepository.save(user);
    }

    private User getUserById(long id) {
        Optional<User> user;
        user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return user.get();
    }
    private boolean exists(UserDto userDto) {
        return userRepository.findByUserNameFirstNameAndUserNameLastName(userDto.getFirstName(), userDto.getLastName()).isPresent();
    }

}
