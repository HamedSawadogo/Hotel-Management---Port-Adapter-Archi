package org.example.domain.ports;

import org.example.domain.models.Client;

public interface ClientPort {
    Client getOneById(Long clientId);
}
