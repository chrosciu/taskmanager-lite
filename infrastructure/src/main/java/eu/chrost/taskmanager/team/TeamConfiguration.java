package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.SpringEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TeamConfiguration {
    @Bean
    public TeamFacade teamFacade(TeamRepository teamRepository,
                                 TeamQueryRepository teamQueryRepository,
                                 SpringEventPublisher springEventPublisher) {
        return new TeamFacade(teamRepository, teamQueryRepository, springEventPublisher);
    }
}
