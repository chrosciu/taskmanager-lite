package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.common.event.TeamMembersEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RabbitListener(queues = "taskmanager")
@RequiredArgsConstructor
class RabbitUserEventListener {
    private final UserFacade userFacade;

    @RabbitHandler
    @Transactional
    public void receive(TeamMembersEvent teamMembersEvent) {
        userFacade.handle(teamMembersEvent);
    }
}
