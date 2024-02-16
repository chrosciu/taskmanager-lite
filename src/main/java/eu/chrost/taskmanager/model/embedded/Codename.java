package eu.chrost.taskmanager.model.embedded;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Codename {
    private String shortName;
    private String fullName;
}
