package eu.chrost.taskmanager.user;

import org.springframework.data.repository.Repository;

interface JpaUserQueryRepository extends Repository<JpaUser, Long>, UserQueryRepository {
}
