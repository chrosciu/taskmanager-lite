package eu.chrost.taskmanager.team.infrastructure;

import eu.chrost.taskmanager.team.application.TeamFacade;
import eu.chrost.taskmanager.team.application.TeamQueryRepository;
import eu.chrost.taskmanager.team.domain.TeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TeamConfiguration {
    @Bean
    public TeamFacade teamFacade(TeamRepository teamRepository, TeamQueryRepository teamQueryRepository) {
        return new TeamFacade(teamRepository, teamQueryRepository);
    }
}
