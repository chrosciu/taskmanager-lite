package eu.chrost.taskmanager.infrastructure.jpa.user;

import eu.chrost.taskmanager.user.application.UserQueryRepository;
import eu.chrost.taskmanager.user.application.dto.UserQueryDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface JpaUserQueryRepository extends Repository<JpaUser, Long>, UserQueryRepository {
    <T> List<T> findBy(Class<T> type);

    default List<UserQueryDto> findBy() {
        return findBy(JpaUserQueryDto.class).stream().map(u -> (UserQueryDto)u).toList();
    }

    <T> Optional<T> findDtoById(long id, Class<T> type);

    default Optional<UserQueryDto> findDtoById(long id) {
        return findDtoById(id, JpaUserQueryDto.class).map(u -> u);
    }

}
