package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserFullDto;
import eu.chrost.taskmanager.user.dto.UserShortDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

class InMemoryUserRepository implements UserRepository, UserQueryRepository {
    private final Map<Long, User> usersMap;
    private final Random random = new Random();

    public InMemoryUserRepository(User... users) {
        this.usersMap = new HashMap<>(users.length);
        for (User user : users) {
            save(user);
        }
    }

    @Override
    public User save(User user) {
        if (null == user.getId()) {
            while (true) {
                long newId = random.nextLong();
                if (!usersMap.containsKey(newId)) {
                    user.setId(newId);
                    break;
                }
            }
        }
        usersMap.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(User user) {
        usersMap.remove(user.getId());
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(usersMap.get(id));
    }

    @Override
    public List<UserShortDto> findBy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<UserFullDto> findDtoById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsByUserNameFirstNameAndUserNameLastName(String firstName, String lastName) {
        var username = new UserName();
        username.setFirstName(firstName);
        username.setLastName(lastName);
        return usersMap.entrySet().stream()
                .anyMatch(entry -> Objects.equals(entry.getValue().getUserName(), username));
    }
}
