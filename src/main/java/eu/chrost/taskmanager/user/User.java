package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.team.query.SimpleTeamQueryDto;
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
class User {
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
    private List<SimpleTeamQueryDto> teams = new ArrayList<>();

    public List<SimpleTeamQueryDto> getTeams() {
        return List.copyOf(teams);
    }

    public void addToTeam(SimpleTeamQueryDto team) {
        teams.add(team);
    }

    public void removeFrom(SimpleTeamQueryDto team) {
        teams.remove(team);
    }
}
