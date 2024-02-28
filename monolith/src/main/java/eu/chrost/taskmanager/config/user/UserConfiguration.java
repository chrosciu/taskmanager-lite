package eu.chrost.taskmanager.config.user;

import eu.chrost.taskmanager.user.application.UserFacade;
import eu.chrost.taskmanager.user.application.UserQueryRepository;
import eu.chrost.taskmanager.user.domain.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserConfiguration {
    @Bean
    public UserFacade userFacade(UserRepository userRepository, UserQueryRepository userQueryRepository) {
        return new UserFacade(userRepository, userQueryRepository);
    }
}
