package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.commons.event.Event;
import eu.chrost.taskmanager.commons.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RabbitTeamEventPublisher implements EventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Event event) {
        rabbitTemplate.convertAndSend("taskmanager", event);
    }
}
