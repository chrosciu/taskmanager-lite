package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.internal.SimpleTeamQueryEntity;
import eu.chrost.taskmanager.user.internal.User;
import eu.chrost.taskmanager.user.internal.UserAlreadyExistsException;
import eu.chrost.taskmanager.user.internal.UserDto;
import eu.chrost.taskmanager.user.internal.UserName;
import eu.chrost.taskmanager.user.internal.UserNotFoundException;
import eu.chrost.taskmanager.user.internal.UserQueryRepository;
import eu.chrost.taskmanager.user.internal.UserRepository;
import eu.chrost.taskmanager.user.internal.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;

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

    public void addTeamToUsersTeams(TeamMembersDto teamMembersDto, long teamId) throws UserNotFoundException {
        for (long userId : teamMembersDto.getUserIds()) {
            User user = getUserById(userId);
            SimpleTeamQueryEntity team = new SimpleTeamQueryEntity(teamId);
            user.addToTeam(team);
            userRepository.save(user);
        }
    }

    public void removeTeamFromUsersTeams(TeamMembersDto teamMembersDto, long teamId) throws UserNotFoundException {
        for (long userId : teamMembersDto.getUserIds()) {
            User user = getUserById(userId);
            SimpleTeamQueryEntity team = new SimpleTeamQueryEntity(teamId);
            user.removeFrom(team);
            userRepository.save(user);
        }
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
        return userQueryRepository.existsByUserNameFirstNameAndUserNameLastName(userDto.getFirstName(), userDto.getLastName());
    }
}
