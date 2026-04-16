package org.example.domain.models;

import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Reservation {
    private Long id;
    private LocalDateTime dateReservation;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private Hebergement hebergement;
    private  StatusReservation status;
    private int nbPersonnes;
    private int nbJours;
    private int nombreEnfants;
    private Client client;

    public Reservation(LocalDateTime dateReservation,
                       Hebergement hebergement,
                       int nbPersonnes,
                       int nbJours ,
                       int nombreEnfants,
                       Client client
    ) {
        this.dateReservation = dateReservation;
        this.hebergement = hebergement;
        this.nbPersonnes = nbPersonnes;
        this.nbJours = nbJours;
        this.nombreEnfants = nombreEnfants;
        this.client = client;
        this.status = StatusReservation.EN_ATTENTE;
    }

    public void terminer() {
        if (this.status != StatusReservation.TERMINE) {
            this.status = StatusReservation.TERMINE;
        }
    }

    public void confirmer() {
       if (this.status != StatusReservation.CONFIRME) {
           this.status = StatusReservation.CONFIRME;
           this.hebergement.occuper();
       }
    }

    public void annuler() {
        if (this.status == StatusReservation.CONFIRME) {
            throw new BusinessException("Impossible d'annuler une réservation confirmée");
        }
        this.status = StatusReservation.ANULLEE;
        this.hebergement.liberer();
    }
}
