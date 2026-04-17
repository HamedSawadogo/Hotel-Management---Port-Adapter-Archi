package org.example.domain.services;

import org.example.domain.models.Reservation;
import org.example.domain.models.Sejour;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class PricingService {

    public BigDecimal getReservationPrice(Reservation reservation, Optional<Sejour> sejour) {
        BigDecimal montantHebergement = reservation.getHebergement().getPrixParNuit().multiply(BigDecimal.valueOf(reservation.getNbJours()));
        return sejour.map(s -> s.getMontant().add(montantHebergement)).orElse(montantHebergement);
    }

}
