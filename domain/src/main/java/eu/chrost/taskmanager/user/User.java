package eu.chrost.taskmanager.user;

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

    private List<UserTeam> teams = new ArrayList<>();

    public List<UserTeam> getTeams() {
        return List.copyOf(teams);
    }

    public void addToTeam(UserTeam team) {
        teams.add(team);
    }

    public void removeFrom(UserTeam team) {
        teams.remove(team);
    }
}
