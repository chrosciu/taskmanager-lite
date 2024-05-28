package eu.chrost.taskmanager.common;

import lombok.Data;

import java.util.List;

@Data
public class TeamMembersDto {
    private List<Long> userIds;
}
