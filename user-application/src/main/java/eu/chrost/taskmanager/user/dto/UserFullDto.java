package eu.chrost.taskmanager.user.dto;

import java.util.List;

public interface UserFullDto extends UserShortDto {
    List<Long> getTeamIds();
}
