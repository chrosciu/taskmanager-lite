package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserShortDto;
import org.springframework.beans.factory.annotation.Value;

interface JpaUserShortDto extends UserShortDto {
    @Value("#{target.userName?.firstName}")
    String getFirstName();
    @Value("#{target.userName?.lastName}")
    String getLastName();
}
