package org.example.domain.models;

import lombok.Getter;
import org.example.domain.events.BaseEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ReservationConfirmee extends BaseEvent<Reservation> {
    private final Reservation reservation;

    public ReservationConfirmee(Reservation reservation) {
        super(reservation, LocalDateTime.now());
        this.reservation = reservation;
    }
}
