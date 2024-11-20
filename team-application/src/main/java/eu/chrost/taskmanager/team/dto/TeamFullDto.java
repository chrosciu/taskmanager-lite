package eu.chrost.taskmanager.team.dto;

import java.util.List;

public interface TeamFullDto extends TeamShortDto {
    List<Long> getUserIds();
}
