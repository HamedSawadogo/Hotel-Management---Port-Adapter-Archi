package org.example.application.usecases;

import org.example.domain.models.FactureClient;
import org.example.domain.models.Reservation;
import org.example.application.ports.FacturePort;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreerFactureUseCase {
    private final FacturePort facturePort;

    public CreerFactureUseCase(FacturePort facturePort) {
        this.facturePort = facturePort;
    }

    public FactureClient creer(Reservation reservation, BigDecimal montant) {
        final FactureClient factureClient = new FactureClient(montant, reservation, LocalDateTime.now());
        return facturePort.creer(factureClient);
    }
}
