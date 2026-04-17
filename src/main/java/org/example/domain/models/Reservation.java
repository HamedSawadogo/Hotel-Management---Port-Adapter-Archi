package org.example.domain.models;

import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
public class Reservation {
    private Long id;
    private LocalDateTime dateReservation;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private Hebergement hebergement;
    private StatusReservation status;
    private int nbPersonnes;
    private long nbJours;
    private int nombreEnfants;
    private final Client client;

    public Reservation(LocalDateTime dateReservation,
                       LocalDate dateArrivee,
                       LocalDate dateDepart,
                       Hebergement hebergement,
                       int nbPersonnes,
                       int nombreEnfants,
                       Client client) {
        this.dateReservation = dateReservation;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.hebergement = hebergement;
        this.nbPersonnes = nbPersonnes;
        this.nbJours = ChronoUnit.DAYS.between(dateArrivee, dateDepart);
        this.nombreEnfants = nombreEnfants;
        this.client = client;
        this.status = StatusReservation.EN_ATTENTE;
    }

    public void update(Reservation reservation) {
        if (reservation.getStatus().equals(StatusReservation.ANULLEE)  ||
        reservation.getStatus().equals(StatusReservation.TERMINE)) {
            throw new BusinessException("Impossible de modifier une reservation annulée ou terminée");
        }

        if (this.dateReservation.isAfter(reservation.dateReservation)) {
            throw new BusinessException("la date de reservation est invalide");
        }
        this.status = reservation.status;
        this.dateReservation = reservation.dateReservation;
        this.nbJours = reservation.nbJours;
        this.nombreEnfants = reservation.nombreEnfants;
    }

    public void terminer() {
        if (this.status == StatusReservation.ANULLEE) {
            throw new BusinessException("Impossible de terminer une réservation annulée");
        }
        this.status = StatusReservation.TERMINE;
    }

    public void confirmer() {
        if (this.status == StatusReservation.ANULLEE || this.status == StatusReservation.TERMINE) {
            throw new BusinessException("Impossible de confirmer une réservation " + this.status.name().toLowerCase());
        }
        if (this.status == StatusReservation.CONFIRME) {
            return;
        }
        this.status = StatusReservation.CONFIRME;
        this.hebergement.occuper();
    }

    public void annuler() {
        if (this.status == StatusReservation.CONFIRME) {
            throw new BusinessException("Impossible d'annuler une réservation confirmée");
        }
        if (this.status == StatusReservation.ANULLEE) {
            throw new BusinessException("La réservation est déjà annulée");
        }
        this.status = StatusReservation.ANULLEE;
    }
}
