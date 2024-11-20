package eu.chrost.taskmanager.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "teams")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class JpaSimpleTeam {
    @Id
    private long id;
}
