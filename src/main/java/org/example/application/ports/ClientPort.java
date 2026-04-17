package org.example.application.ports;

import org.example.domain.models.Client;

public interface ClientPort {
    Client findById(Long clientId);
}
