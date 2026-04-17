package org.example.application.ports;

import org.example.domain.models.Hebergement;

public interface HebergementPort {
    Hebergement findById(Long hebergementId);
}
