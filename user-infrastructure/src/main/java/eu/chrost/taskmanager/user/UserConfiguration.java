package eu.chrost.taskmanager.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserConfiguration {
    @Bean
    public UserFacade userFacade(UserRepository userRepository, UserQueryRepository userQueryRepository) {
        return new UserFacade(userRepository, userQueryRepository);
    }
}
