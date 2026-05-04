package org.example.application.ports.in;

import org.example.application.dtos.PaymentRequest;

public interface ConfirmReservationUseCase {
    void confirmer(Long reservationId, PaymentRequest paymentRequest);
}
