package org.example.domain.ports.out.repositories;

import org.example.domain.models.Reservation;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationPort {
    Reservation creer(Reservation reservation);
    Reservation sauvegarder(Reservation reservation);
    Reservation findById(Long reservationId);

    int countAllByCurrentUse(String authUser);
    Optional<Reservation> getReservationBetweenByHebergement(LocalDate dateArrivee, LocalDate dateDepart, Long hebergementId);
}
