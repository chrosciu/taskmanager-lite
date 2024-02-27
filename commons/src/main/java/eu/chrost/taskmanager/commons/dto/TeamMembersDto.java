package eu.chrost.taskmanager.commons.dto;

import lombok.Data;

import java.util.List;


//FIXME: this DTO should be defined in web adapter and repacked into two separate DTOs defined in team and user application modules
@Data
public class TeamMembersDto {
    private List<Long> userIds;
}
