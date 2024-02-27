package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.commons.SimpleEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class User {
    private Long id;
    private String login;
    private String password;

    private UserName userName;

    private UserRole userRole;

    private List<SimpleEntity> teams = new ArrayList<>();

    public List<SimpleEntity> getTeams() {
        return List.copyOf(teams);
    }

    public void addToTeam(SimpleEntity team) {
        teams.add(team);
    }

    public void removeFrom(SimpleEntity team) {
        teams.remove(team);
    }
}
