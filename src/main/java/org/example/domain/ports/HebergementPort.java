package org.example.domain.ports;

import org.example.domain.models.Hebergement;

public interface HebergementPort {
    Hebergement getOneById(Long herbergementId);
}
