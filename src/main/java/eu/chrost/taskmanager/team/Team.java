package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.user.query.SimpleUserQueryDto;
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
@EqualsAndHashCode
class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Codename codename;

    private String description;

    @OneToMany
    private List<SimpleUserQueryDto> members = new ArrayList<>();

    public List<SimpleUserQueryDto> getMembers() {
        return List.copyOf(members);
    }

    public void addMember(SimpleUserQueryDto user) {
        members.add(user);
    }

    public void removeMember(SimpleUserQueryDto user) {
        members.remove(user);
    }
}


