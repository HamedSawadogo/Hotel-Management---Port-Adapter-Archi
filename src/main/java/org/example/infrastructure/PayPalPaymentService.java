package org.example.infrastructure;

import org.example.application.dtos.PaymentRequest;
import org.example.domain.repositories.PaymentServicePort;

public class PayPalPaymentService implements PaymentServicePort {
    @Override
    public void payer(PaymentRequest paymentRequest) {
        // TODO: appel réel à l'API PayPal
        System.out.println("Paiement de " + paymentRequest.amount() + " XOF via PayPal");
    }
}
