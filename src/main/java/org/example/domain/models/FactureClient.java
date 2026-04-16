package org.example.domain.models;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class FactureClient {
    private Long id;
    private BigDecimal montant;
    private Reservation reservation;
    private LocalDateTime date;
    private FactureStatus factureStatus;

    public FactureClient(BigDecimal montant, Reservation reservation, LocalDateTime date) {
        this.montant = montant;
        this.reservation = reservation;
        this.date = date;
        factureStatus = FactureStatus.EMIS;
    }
}
