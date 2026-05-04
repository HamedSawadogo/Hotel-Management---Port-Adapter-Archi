package org.example.domain.ports.out.eventpublisher;

import org.example.domain.events.BaseEvent;

public interface ReservationEventPublisher {
    void publish(final BaseEvent event);
}
