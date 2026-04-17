package org.example.domain.models;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class DomainEvent {
    protected final UUID id;
    protected final LocalDateTime occurredAt;

    protected DomainEvent() {
        this.id = UUID.randomUUID();
        this.occurredAt = LocalDateTime.now();
    }
}
