package eu.chrost.taskmanager.team.dto;

import org.springframework.beans.factory.annotation.Value;

public interface TeamShortDto {
    Long getId();
    String getName();
    String getCodenameShort();
    String getCodenameFull();
    String getDescription();
}
