package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserQueryDto;
import org.springframework.beans.factory.annotation.Value;

interface JpaUserQueryDto extends UserQueryDto {
    @Value("#{target.userName?.firstName}")
    String getFirstName();
    @Value("#{target.userName?.lastName}")
    String getLastName();
}
