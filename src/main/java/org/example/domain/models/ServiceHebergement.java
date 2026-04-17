package org.example.domain.models;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ServiceHebergement {
    private Long id;
    private String name;
    private TypeServiceHebergement typeServiceHebergement;
    private BigDecimal prix;
}

