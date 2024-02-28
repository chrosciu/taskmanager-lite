package eu.chrost.taskmanager.team.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private Long id;
    private String name;
    private String codenameShort;
    private String codenameFull;
    private String description;
    private List<Long> userIds;
}
