package eu.chrost.taskmanager.team.internal;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@EqualsAndHashCode(exclude = "members")
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Codename codename;

    private String description;

    @OneToMany
    private List<SimpleUserQueryEntity> members = new ArrayList<>();

    public List<SimpleUserQueryEntity> getMembers() {
        return List.copyOf(members);
    }

    public void addMember(SimpleUserQueryEntity user) {
        members.add(user);
    }

    public void removeMember(SimpleUserQueryEntity user) {
        members.remove(user);
    }

    public List<Long> getMemberIds() {
        return members.stream().map(SimpleUserQueryEntity::getId).toList();
    }
}


