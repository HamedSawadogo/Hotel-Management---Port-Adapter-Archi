package org.example.domain.ports;

import org.example.domain.models.Reservation;

public interface ReservationPort {
    Reservation creer(Reservation reservation);

    Reservation findOneById(Long reservationId);
}
