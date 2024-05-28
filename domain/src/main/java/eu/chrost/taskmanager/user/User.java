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

    private List<SimpleTeam> teams = new ArrayList<>();

    public List<SimpleTeam> getTeams() {
        return List.copyOf(teams);
    }

    public void addToTeam(SimpleTeam team) {
        teams.add(team);
    }

    public void removeFrom(SimpleTeam team) {
        teams.remove(team);
    }

    public List<Long> getTeamIds() {
        return teams.stream().map(SimpleTeam::id).toList();
    }
}
