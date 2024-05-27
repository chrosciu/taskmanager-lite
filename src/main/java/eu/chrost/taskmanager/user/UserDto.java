package eu.chrost.taskmanager.user;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String userRole;
    private List<Long> teamIds = new ArrayList<>();
}
