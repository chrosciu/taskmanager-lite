package eu.chrost.taskmanager.commons.event;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class TeamMembersEvent implements Event {
    public enum Type {
        MEMBERS_ADDED,
        MEMBERS_REMOVED
    }

    public TeamMembersEvent(Type type, long teamId, List<Long> userIds) {
        this.type = type;
        this.teamId = teamId;
        this.userIds = userIds;
        this.timestamp = Instant.now();
    }

    private final Type type;
    private final long teamId;
    private final List<Long> userIds;
    private final Instant timestamp;
}
