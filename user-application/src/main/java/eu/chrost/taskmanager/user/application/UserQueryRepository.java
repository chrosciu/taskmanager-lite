package eu.chrost.taskmanager.user.application;

import eu.chrost.taskmanager.user.application.dto.UserQueryDto;

import java.util.List;
import java.util.Optional;

public interface UserQueryRepository {
    List<UserQueryDto> findBy();

    Optional<UserQueryDto> findDtoById(long id);

    boolean existsByUserNameFirstNameAndUserNameLastName(String firstName, String lastName);
}
