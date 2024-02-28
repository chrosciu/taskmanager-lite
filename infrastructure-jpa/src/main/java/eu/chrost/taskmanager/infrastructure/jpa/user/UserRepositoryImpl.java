package eu.chrost.taskmanager.infrastructure.jpa.user;

import eu.chrost.taskmanager.user.domain.User;
import eu.chrost.taskmanager.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(JpaUser.fromUser(user)).toUser();
    }

    @Override
    public void delete(User user) {
        userRepository.delete(JpaUser.fromUser(user));
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id).map(JpaUser::toUser);
    }
}
