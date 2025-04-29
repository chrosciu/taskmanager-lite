package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserFullDto;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

interface JpaUserFullDto extends UserFullDto, JpaUserShortDto {
    @Value("#{target.teamIds}")
    List<Long> getTeamIds();
}
