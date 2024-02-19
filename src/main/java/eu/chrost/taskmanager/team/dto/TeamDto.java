package eu.chrost.taskmanager.team.dto;

import lombok.Data;

import java.util.List;

@Data
class TeamDto {
    private Long id;
    private String name;
    private String codenameShort;
    private String codenameFull;
    private String description;
    private List<Long> userIds;
}
