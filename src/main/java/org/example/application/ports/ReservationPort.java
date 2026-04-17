package org.example.application.ports;

import org.example.domain.models.Reservation;

public interface ReservationPort {
    Reservation creer(Reservation reservation);
    Reservation sauvegarder(Reservation reservation);
    Reservation findById(Long reservationId);
}
