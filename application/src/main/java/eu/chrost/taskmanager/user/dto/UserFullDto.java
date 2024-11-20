package eu.chrost.taskmanager.user.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserFullDto extends UserShortDto {
    @Value("#{target.teamIds}")
    List<Long> getTeamIds();
}
