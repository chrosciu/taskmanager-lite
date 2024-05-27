package eu.chrost.taskmanager.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String userRole;
    private List<Long> teamIds;
}
