package eu.chrost.taskmanager.user.dto;

public interface UserShortDto {
    Long getId();
    String getFirstName();
    String getLastName();
    String getLogin();
    String getPassword();
    String getUserRole();
}
