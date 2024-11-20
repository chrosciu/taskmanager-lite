package eu.chrost.taskmanager.user;

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
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class JpaUser {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String login;
    private String password;

    @Embedded
    private UserName userName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany
    private List<JpaSimpleTeam> teams = new ArrayList<>();

    public List<Long> getTeamIds() {
        return teams.stream().map(JpaSimpleTeam::getId).toList();
    }

    static JpaUser fromUser(User user) {
        JpaUser jpaUser = new JpaUser();
        jpaUser.id = user.getId();
        jpaUser.login = user.getLogin();
        jpaUser.password = user.getPassword();
        jpaUser.userName = user.getUserName();
        jpaUser.userRole = user.getUserRole();
        jpaUser.teams = user.getTeams().stream().map(t -> new JpaSimpleTeam(t.id())).toList();
        return jpaUser;
    }

    User toUser() {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setUserName(userName);
        user.setUserRole(userRole);
        user.setTeams(teams.stream().map(t -> new SimpleTeam(t.getId())).collect(Collectors.toCollection(ArrayList::new)));
        return user;
    }
}
