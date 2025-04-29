package eu.chrost.taskmanager.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class TeamMembersEvent implements Event {
    public enum Type {
        MEMBERS_ADDED
    }

    private final Type type;
    private final long teamId;
    private final List<Long> userIds;
}
