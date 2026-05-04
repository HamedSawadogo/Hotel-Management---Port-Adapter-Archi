package org.example.domain.services;

import java.math.BigDecimal;

public class PromotionByFidelisation implements PromotionStrategy {
    @Override
    public BigDecimal apply(BigDecimal baseAmount) {
        return baseAmount.subtract(baseAmount.multiply(new BigDecimal("0.5")));
    }
}
