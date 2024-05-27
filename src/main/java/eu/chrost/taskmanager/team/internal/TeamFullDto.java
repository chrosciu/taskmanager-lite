package eu.chrost.taskmanager.team.internal;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface TeamFullDto extends TeamShortDto {
    @Value("#{target.memberIds}")
    List<Long> getUserIds();
}
