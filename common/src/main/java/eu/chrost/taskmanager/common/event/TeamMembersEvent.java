package eu.chrost.taskmanager.common.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class TeamMembersEvent implements Event {
    public enum Type {
        MEMBERS_ADDED,
        MEMBERS_REMOVED
    }

    public TeamMembersEvent(Type type, long teamId, List<Long> userIds) {
        this.type = type;
        this.teamId = teamId;
        this.userIds = userIds;
    }

    private final Type type;
    private final long teamId;
    private final List<Long> userIds;
}
