package eu.chrost.taskmanager.team;

import lombok.Data;

import java.util.List;

@Data
class TeamMembersDto {
    private List<Long> userIds;
}
