package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.commons.event.Event;
import eu.chrost.taskmanager.commons.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SpringTeamEventPublisher implements EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(Event event) {
        applicationEventPublisher.publishEvent(event);
    }
}
