package eu.chrost.taskmanager.user.internal;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserQueryRepository extends Repository<User, Long> {
    List<UserShortDto> findBy();

    Optional<UserFullDto> findDtoById(long id);

    boolean existsByUserNameFirstNameAndUserNameLastName(String firstName, String lastName);
}
