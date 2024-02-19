package eu.chrost.taskmanager.team.dto;

import lombok.Data;

import java.util.List;

@Data
class TeamMembersDto {
    private List<Long> userIds;
}
