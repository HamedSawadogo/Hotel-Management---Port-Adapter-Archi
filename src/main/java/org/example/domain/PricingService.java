package org.example.domain;

import org.example.domain.models.Reservation;
import org.example.domain.models.Sejour;
import org.example.domain.ports.HebergementPort;
import org.example.domain.ports.ReservationPort;
import org.example.domain.ports.SejourPort;
import java.math.BigDecimal;
import java.util.Optional;

public class PricingService {
    private final ReservationPort reservationPort;
    private final HebergementPort hebergementPort;
    private final SejourPort sejourPort;

    public PricingService(ReservationPort reservationPort, HebergementPort hebergementPort, SejourPort sejourPort) {
        this.reservationPort = reservationPort;
        this.hebergementPort = hebergementPort;
        this.sejourPort = sejourPort;
    }

    public BigDecimal getReservationPrice(Reservation reservation) {
        final int prixHebergement = hebergementPort.getOneById(reservation.getHebergement().getId()).getPrixParNuit();
        final int nombreDeJours = reservation.getNbJours();
        final Optional<Sejour> sejour =  sejourPort.getIfExist(reservation);

        final BigDecimal montantReservation = BigDecimal.valueOf((long) prixHebergement * nombreDeJours);
        if (sejour.isEmpty()) {
            return montantReservation;
        }
        final BigDecimal coutFuturSejourAlaReservation = sejour.get().getMotant();
        return coutFuturSejourAlaReservation.add(montantReservation);
    }
}
