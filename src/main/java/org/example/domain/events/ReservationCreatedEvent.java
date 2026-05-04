package org.example.domain.events;

import lombok.Getter;
import org.example.domain.models.Reservation;
import java.time.LocalDateTime;

@Getter
public class ReservationCreatedEvent extends BaseEvent<Reservation> {
    public ReservationCreatedEvent(Reservation entity) {
        super(entity, LocalDateTime.now());
    }
}
