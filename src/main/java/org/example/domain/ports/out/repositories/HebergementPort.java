package org.example.domain.ports.out.repositories;

import org.example.domain.models.Hebergement;

import java.util.Optional;

public interface HebergementPort {
    Optional<Hebergement> findById(Long hebergementId);
}
