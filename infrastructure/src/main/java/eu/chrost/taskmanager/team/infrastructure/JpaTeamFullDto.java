package eu.chrost.taskmanager.team.infrastructure;

import eu.chrost.taskmanager.team.application.dto.TeamFullDto;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

interface JpaTeamFullDto extends TeamFullDto, JpaTeamShortDto {
    @Value("#{target.memberIds}")
    List<Long> getUserIds();
}
