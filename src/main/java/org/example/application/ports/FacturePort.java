package org.example.application.ports;

import org.example.domain.models.FactureClient;

public interface FacturePort {
    FactureClient creer(FactureClient factureClient);
}
