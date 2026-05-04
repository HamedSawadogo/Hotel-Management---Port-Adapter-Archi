package org.example.domain.services;

import org.example.domain.models.BusinessException;

import java.math.BigDecimal;


public class DefaultPaymentPolicy implements PaymentPolicy {
    private static final String TAUX_MINIMUN_PAIEMENT = "0.30"; // en % soit 30%

    @Override
    public boolean validaterMontantMinimumPayment(BigDecimal montanPayment, BigDecimal montantTotal) {
        BigDecimal seuilMinimum = montantTotal.multiply(new BigDecimal(TAUX_MINIMUN_PAIEMENT));
        return montanPayment.compareTo(seuilMinimum) >= 0;
    }
}
