package eu.chrost.taskmanager.team.application.dto;

import java.util.List;

public interface TeamFullDto extends TeamShortDto {
    List<Long> getUserIds();
}
