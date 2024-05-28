package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserFullDto;
import eu.chrost.taskmanager.user.dto.UserShortDto;

import java.util.List;
import java.util.Optional;

public interface UserQueryRepository {
    List<UserShortDto> findBy();

    Optional<UserFullDto> findDtoById(long id);

    boolean existsByUserNameFirstNameAndUserNameLastName(String firstName, String lastName);
}
