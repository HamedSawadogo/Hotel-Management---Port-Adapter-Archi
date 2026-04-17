package org.example.domain.repositories;

import org.example.application.dtos.PaymentRequest;

public interface PaymentServicePort {
    void payer(PaymentRequest paymentRequest);
}
