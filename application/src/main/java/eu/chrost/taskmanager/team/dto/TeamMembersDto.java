package eu.chrost.taskmanager.team.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamMembersDto {
    private List<Long> userIds;
}
