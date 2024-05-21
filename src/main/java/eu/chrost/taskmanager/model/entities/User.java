package eu.chrost.taskmanager.model.entities;

import eu.chrost.taskmanager.model.embedded.UserName;
import eu.chrost.taskmanager.model.enums.TeamRole;
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
    private TeamRole teamRole;

    @OneToMany
    private List<Team> teams = new ArrayList<>();

    public List<Team> getTeams() {
        return List.copyOf(teams);
    }

    public void addToTeam(Team team) {
        teams.add(team);
    }

    public void removeFrom(Team team) {
        if (!teams.contains(team)) {
            throw new RuntimeException();
        }
        teams.remove(team);
    }
}