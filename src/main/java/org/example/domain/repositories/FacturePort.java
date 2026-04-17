package org.example.domain.repositories;

import org.example.domain.models.FactureClient;

public interface FacturePort {
    FactureClient creer(FactureClient factureClient);
}
