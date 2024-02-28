package eu.chrost.taskmanager.team.domain;

import eu.chrost.taskmanager.commons.SimpleEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Team {
    private Long id;

    private String name;

    private Codename codename;

    private String description;

    private List<SimpleEntity> members = new ArrayList<>();

    public List<SimpleEntity> getMembers() {
        return List.copyOf(members);
    }

    public void addMember(SimpleEntity user) {
        members.add(user);
    }

    public void removeMember(SimpleEntity user) {
        members.remove(user);
    }

    public List<Long> getMemberIds() {
        return members.stream().map(SimpleEntity::id).toList();
    }
}


