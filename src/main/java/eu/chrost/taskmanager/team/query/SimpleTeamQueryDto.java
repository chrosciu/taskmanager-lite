package eu.chrost.taskmanager.team.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "teams")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTeamQueryDto {
    @Id
    private long id;
}
