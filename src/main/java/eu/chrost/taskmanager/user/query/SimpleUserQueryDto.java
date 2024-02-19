package eu.chrost.taskmanager.user.query;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "users")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserQueryDto {
    @Id
    private long id;
}
