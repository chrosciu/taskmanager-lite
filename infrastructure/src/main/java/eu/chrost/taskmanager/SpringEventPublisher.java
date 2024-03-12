package eu.chrost.taskmanager;

import eu.chrost.taskmanager.commons.event.Event;
import eu.chrost.taskmanager.commons.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(Event event) {
        applicationEventPublisher.publishEvent(event);
    }
}
