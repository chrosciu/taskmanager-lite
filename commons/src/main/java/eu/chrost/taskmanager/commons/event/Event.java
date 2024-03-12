package eu.chrost.taskmanager.commons.event;

import java.time.Instant;

public interface Event {
    Instant getTimestamp();
}
