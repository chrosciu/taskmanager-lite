package eu.chrost.taskmanager.common.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TeamMembersEvent implements Event {
    public enum Type {
        MEMBERS_ADDED,
        MEMBERS_REMOVED
    }

    private final Type type;
    private final long teamId;
    private final List<Long> userIds;
}
