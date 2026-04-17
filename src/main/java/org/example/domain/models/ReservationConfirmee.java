package org.example.domain.models;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class ReservationConfirmee extends DomainEvent {
    private final Long reservationId;
    private final Long clientId;
    private final BigDecimal montantTotal;

    public ReservationConfirmee(Long reservationId, Long clientId, BigDecimal montantTotal) {
        super();
        this.reservationId = reservationId;
        this.clientId = clientId;
        this.montantTotal = montantTotal;
    }
}
