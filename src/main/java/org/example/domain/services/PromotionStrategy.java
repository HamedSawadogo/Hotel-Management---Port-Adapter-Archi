package org.example.domain.services;

import java.math.BigDecimal;

public interface PromotionStrategy {
    BigDecimal apply(BigDecimal baseAmount);
}
