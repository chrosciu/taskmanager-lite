package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.user.JpaSimpleUser;
import eu.chrost.taskmanager.user.dto.SimpleUser;
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
import java.util.stream.Collectors;

@Entity
@Table(name = "teams")
@Getter
@Setter
@EqualsAndHashCode
class JpaTeam {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Codename codename;

    private String description;

    @OneToMany
    private List<JpaSimpleUser> members = new ArrayList<>();

    public List<Long> getMemberIds() {
        return members.stream().map(JpaSimpleUser::getId).toList();
    }

    static JpaTeam fromTeam(Team team) {
        JpaTeam jpaTeam = new JpaTeam();
        jpaTeam.id = team.getId();
        jpaTeam.name = team.getName();
        jpaTeam.codename = team.getCodename();
        jpaTeam.description = team.getDescription();
        jpaTeam.members = team.getMembers().stream().map(u -> new JpaSimpleUser(u.id())).toList();
        return jpaTeam;
    }

    public Team toTeam() {
        Team team = new Team();
        team.setId(id);
        team.setName(name);
        team.setCodename(codename);
        team.setDescription(description);
        team.setMembers(members.stream().map(u -> new SimpleUser(u.getId())).collect(Collectors.toCollection(ArrayList::new)));
        return team;
    }
}
