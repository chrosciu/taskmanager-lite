package eu.chrost.taskmanager.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
class UserName {
    private String firstName;
    private String lastName;
}
