package eu.chrost.taskmanager.user.internal;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "users")
@Setter
@Getter
@EqualsAndHashCode(exclude = "teams")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;

    @Embedded
    private UserName userName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany
    private List<SimpleTeamQueryEntity> teams = new ArrayList<>();

    public List<SimpleTeamQueryEntity> getTeams() {
        return List.copyOf(teams);
    }

    public void addToTeam(SimpleTeamQueryEntity team) {
        teams.add(team);
    }

    public void removeFrom(SimpleTeamQueryEntity team) {
        teams.remove(team);
    }

    public List<Long> getTeamIds() {
        return teams.stream().map(SimpleTeamQueryEntity::getId).toList();
    }
}
