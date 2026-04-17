package org.example.domain.gateways;

import org.example.domain.models.Client;

public interface ClientPort {
    Client findById(Long clientId);
}
