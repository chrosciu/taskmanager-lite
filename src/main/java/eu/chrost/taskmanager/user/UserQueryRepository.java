package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserQueryDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserQueryRepository extends Repository<User, Long> {
    List<UserQueryDto> findBy();

    Optional<UserQueryDto> findDtoById(long id);

    boolean existsByUserNameFirstNameAndUserNameLastName(String firstName, String lastName);
}
