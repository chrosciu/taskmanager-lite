package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.common.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisher implements eu.chrost.taskmanager.common.event.EventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Event event) {
        rabbitTemplate.convertAndSend("taskmanager", event);
    }
}
