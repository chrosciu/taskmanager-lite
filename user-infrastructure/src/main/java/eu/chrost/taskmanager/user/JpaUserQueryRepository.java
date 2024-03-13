package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserFullDto;
import eu.chrost.taskmanager.user.dto.UserShortDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface JpaUserQueryRepository extends Repository<JpaUser, Long>, UserQueryRepository {
    <T> List<T> findBy(Class<T> type);

    default List<UserShortDto> findBy() {
        return findBy(JpaUserShortDto.class).stream().map(u -> (UserShortDto)u).toList();
    }

    <T> Optional<T> findDtoById(long id, Class<T> type);

    default Optional<UserFullDto> findDtoById(long id) {
        return findDtoById(id, JpaUserFullDto.class).map(u -> u);
    }

}
