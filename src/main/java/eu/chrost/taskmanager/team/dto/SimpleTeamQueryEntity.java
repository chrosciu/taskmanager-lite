package eu.chrost.taskmanager.team.dto;

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
public class SimpleTeamQueryEntity {
    @Id
    private long id;
}