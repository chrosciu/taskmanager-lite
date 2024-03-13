package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.team.dto.TeamShortDto;
import org.springframework.beans.factory.annotation.Value;

interface JpaTeamShortDto extends TeamShortDto {
    @Value("#{target.codename?.shortName}")
    String getCodenameShort();
    @Value("#{target.codename?.fullName}")
    String getCodenameFull();
}
