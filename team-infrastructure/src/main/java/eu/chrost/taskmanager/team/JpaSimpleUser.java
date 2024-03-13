package eu.chrost.taskmanager.team;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Embeddable
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class JpaSimpleUser {
    private long id;
}
