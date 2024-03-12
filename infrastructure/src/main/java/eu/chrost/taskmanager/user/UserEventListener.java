package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.commons.event.TeamMembersEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserEventListener {
    private final UserFacade userFacade;

    @EventListener
    public void handle(TeamMembersEvent teamMembersEvent) {
        userFacade.handle(teamMembersEvent);
    }
}
