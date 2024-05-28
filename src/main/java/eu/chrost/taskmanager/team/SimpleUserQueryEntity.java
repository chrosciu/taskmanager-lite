package eu.chrost.taskmanager.team;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "users")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class SimpleUserQueryEntity {
    @Id
    private long id;
}
