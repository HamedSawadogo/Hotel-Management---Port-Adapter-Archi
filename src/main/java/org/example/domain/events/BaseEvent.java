package org.example.domain.events;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEvent<E> {
    protected final E entity;
    protected final LocalDateTime createdAt;

    protected BaseEvent(E entity, LocalDateTime createdAt) {
        this.entity = entity;
        this.createdAt = createdAt;
    }
}
