package eu.chrost.taskmanager.user.dto;

import org.springframework.beans.factory.annotation.Value;

public interface UserQueryDto {
    Long getId();
    @Value("#{target.userName?.firstName}")
    String getFirstName();
    @Value("#{target.userName?.lastName}")
    String getLastName();
    String getLogin();
    String getPassword();
    String getUserRole();
}
