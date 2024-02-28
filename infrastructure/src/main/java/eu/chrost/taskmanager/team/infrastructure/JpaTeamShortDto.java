package eu.chrost.taskmanager.team.infrastructure;

import eu.chrost.taskmanager.team.application.dto.TeamShortDto;
import org.springframework.beans.factory.annotation.Value;

interface JpaTeamShortDto extends TeamShortDto {
    @Value("#{target.codename?.shortName}")
    String getCodenameShort();
    @Value("#{target.codename?.fullName}")
    String getCodenameFull();
}
