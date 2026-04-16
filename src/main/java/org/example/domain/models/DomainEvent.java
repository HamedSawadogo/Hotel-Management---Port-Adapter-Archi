package org.example.domain.models;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class DomainEvent {
    protected  Long id;
    protected LocalDateTime occuredAt;
}
