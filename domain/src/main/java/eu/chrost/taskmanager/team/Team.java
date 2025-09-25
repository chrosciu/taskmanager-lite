package eu.chrost.taskmanager.team;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class Team {
    private Long id;

    private String name;

    private Codename codename;

    private String description;

    private List<TeamUser> members = new ArrayList<>();

    public List<TeamUser> getMembers() {
        return List.copyOf(members);
    }

    public void addMember(TeamUser user) {
        members.add(user);
    }

    public void removeMember(TeamUser user) {
        members.remove(user);
    }

    public List<Long> getMemberIds() {
        return members.stream().map(TeamUser::id).toList();
    }
}


