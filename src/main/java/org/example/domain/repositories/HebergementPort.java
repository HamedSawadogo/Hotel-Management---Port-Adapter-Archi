package org.example.domain.repositories;

import org.example.domain.models.Hebergement;

public interface HebergementPort {
    Hebergement findById(Long hebergementId);
}
