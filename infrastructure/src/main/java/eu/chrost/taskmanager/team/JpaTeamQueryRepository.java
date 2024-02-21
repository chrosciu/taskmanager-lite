package eu.chrost.taskmanager.team;

import org.springframework.data.repository.Repository;

interface JpaTeamQueryRepository extends Repository<JpaTeam, Long>, TeamQueryRepository {
}
