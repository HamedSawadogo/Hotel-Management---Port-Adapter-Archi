package org.example.application.ports.in.message.listener;

public interface PaymentResponseMessageListener {
    void paymentCompleted();
    void paymentCancelled();
}
