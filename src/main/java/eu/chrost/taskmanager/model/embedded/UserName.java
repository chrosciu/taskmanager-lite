package eu.chrost.taskmanager.model.embedded;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserName {
    private String firstName;
    private String lastName;
}
