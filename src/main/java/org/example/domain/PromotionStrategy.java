package org.example.domain;

import java.math.BigDecimal;

public interface PromotionStrategy {
    BigDecimal apply(BigDecimal baseAmount);
}
