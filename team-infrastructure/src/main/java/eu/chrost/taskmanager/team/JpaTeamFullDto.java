package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.team.dto.TeamFullDto;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

interface JpaTeamFullDto extends TeamFullDto, JpaTeamShortDto {
    @Value("#{target.memberIds}")
    List<Long> getUserIds();
}
