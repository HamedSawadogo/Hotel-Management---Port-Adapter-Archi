package org.example.infrastructure;

import org.example.domain.gateways.EventPublisher;
import org.example.domain.models.DomainEvent;


public class SpringEventPublisher implements EventPublisher {
    @Override
    public void publish(DomainEvent event) {

    }
}
