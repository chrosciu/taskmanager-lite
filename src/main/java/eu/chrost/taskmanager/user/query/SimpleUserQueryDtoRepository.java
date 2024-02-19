package eu.chrost.taskmanager.user.query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimpleUserQueryDtoRepository extends CrudRepository<SimpleUserQueryDto, Long> {
    Optional<SimpleUserQueryDto> findById(long id);
}
