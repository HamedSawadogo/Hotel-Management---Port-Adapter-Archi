package org.example.domain.gateways;

import org.example.domain.models.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
