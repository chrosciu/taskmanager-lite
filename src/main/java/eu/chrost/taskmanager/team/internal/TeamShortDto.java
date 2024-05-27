package eu.chrost.taskmanager.team.internal;

import org.springframework.beans.factory.annotation.Value;

public interface TeamShortDto {
    Long getId();
    String getName();
    @Value("#{target.codename?.shortName}")
    String getCodenameShort();
    @Value("#{target.codename?.fullName}")
    String getCodenameFull();
    String getDescription();
}
