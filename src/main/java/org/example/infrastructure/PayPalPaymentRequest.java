package org.example.infrastructure;

import org.example.application.dtos.PaymentRequest;
import java.math.BigDecimal;

public record PayPalPaymentRequest(BigDecimal amount, String payPalToken) implements PaymentRequest {}
