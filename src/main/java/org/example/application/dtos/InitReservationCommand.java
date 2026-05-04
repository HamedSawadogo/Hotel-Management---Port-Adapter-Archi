package org.example.application.dtos;

import org.example.domain.models.ServiceHebergement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface InitReservationCommand {
    LocalDateTime getDateReservation();
    LocalDate getDateArrivee();
    LocalDate getDateDepart();
    List<ServiceHebergement> getServices();
    Long getHebergementId();
    Long getClientId();
    int getNbPersonnes();
    int getNbEnfants();
}