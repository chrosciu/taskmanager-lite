package eu.chrost.taskmanager.team;

import lombok.Data;

import java.util.List;

@Data
public class TeamMembersDto {
    private List<Long> userIds;
}
