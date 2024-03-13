package eu.chrost.taskmanager.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.chrost.taskmanager.commons.event.EventPublisher;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TeamConfiguration {
    @Bean
    public TeamFacade teamFacade(TeamRepository teamRepository,
                                 TeamQueryRepository teamQueryRepository,
                                 EventPublisher eventPublisher) {
        return new TeamFacade(teamRepository, teamQueryRepository, eventPublisher);
    }

    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper mapper) {
        final var jsonRabbitTemplate = new RabbitTemplate(connectionFactory);
        jsonRabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(mapper));
        return jsonRabbitTemplate;
    }

    @Bean
    public Queue queue() {
        return new Queue("taskmanager");
    }
}
