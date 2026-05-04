package org.example.domain.ports.out.repositories;

import org.example.application.dtos.PaymentRequest;

public interface PaymentServicePort {
    void payer(PaymentRequest paymentRequest);
}
