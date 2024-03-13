package eu.chrost.taskmanager.team;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TeamConfiguration {
    @Bean
    public TeamFacade teamFacade(TeamRepository teamRepository,
                                 TeamQueryRepository teamQueryRepository,
                                 SpringTeamEventPublisher springTeamEventPublisher) {
        return new TeamFacade(teamRepository, teamQueryRepository, springTeamEventPublisher);
    }
}
