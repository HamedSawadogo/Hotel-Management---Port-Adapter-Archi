package org.example.domain.services;

import java.math.BigDecimal;

public interface PaymentPolicy {
    boolean validaterMontantMinimumPayment(BigDecimal montanPayment, BigDecimal montantTotal);
}
