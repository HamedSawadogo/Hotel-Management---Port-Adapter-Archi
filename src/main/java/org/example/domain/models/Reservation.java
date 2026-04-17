package org.example.domain.models;

import lombok.Getter;
import org.example.domain.PromotionStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private BigDecimal montantLogement;

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
        this.montantLogement = calculerCoutLogement();
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

    public BigDecimal calculerCoutLogement() {
        this.montantLogement =  hebergement.getPrixParNuit().multiply(BigDecimal.valueOf(nbJours));
        return montantLogement;
    }

    public void aplyPromotion( PromotionStrategy promotionStrategy) {
        this.montantLogement = promotionStrategy.apply(montantLogement);
    }


    public BigDecimal getMontantLogement() {
        return montantLogement.setScale(2, RoundingMode.HALF_UP);
    }

}
