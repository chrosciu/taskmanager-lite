package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.user.dto.SimpleUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class Team {
    private Long id;

    private String name;

    private Codename codename;

    private String description;

    private List<SimpleUser> members = new ArrayList<>();

    public List<SimpleUser> getMembers() {
        return List.copyOf(members);
    }

    public void addMember(SimpleUser user) {
        members.add(user);
    }

    public void removeMember(SimpleUser user) {
        members.remove(user);
    }

    public List<Long> getMemberIds() {
        return members.stream().map(SimpleUser::id).toList();
    }
}


