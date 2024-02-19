package eu.chrost.taskmanager.team;

import java.util.List;

public class TeamMembersDto {
    private List<Long> userIds;

    public List<Long> getUserIds() {
        return List.copyOf(userIds);
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = List.copyOf(userIds);
    }
}
