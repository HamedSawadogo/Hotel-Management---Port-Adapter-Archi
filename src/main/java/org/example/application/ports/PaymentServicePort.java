package org.example.application.ports;

import org.example.application.dtos.PaymentRequest;

public interface PaymentServicePort {
    void payer(PaymentRequest paymentRequest);
}
