package eu.chrost.taskmanager.user.infrastructure;

import eu.chrost.taskmanager.user.application.dto.UserQueryDto;
import org.springframework.beans.factory.annotation.Value;

interface JpaUserQueryDto extends UserQueryDto {
    @Value("#{target.userName?.firstName}")
    String getFirstName();
    @Value("#{target.userName?.lastName}")
    String getLastName();
}
