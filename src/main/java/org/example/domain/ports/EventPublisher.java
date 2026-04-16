package org.example.domain.ports;

import org.example.domain.models.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
