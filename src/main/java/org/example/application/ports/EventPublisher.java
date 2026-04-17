package org.example.application.ports;

import org.example.domain.models.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
