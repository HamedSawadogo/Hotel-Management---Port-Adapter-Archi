package org.example.domain.models;

import lombok.Getter;
import org.example.domain.services.PromotionStrategy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
public class Reservation extends AggregateRoot<UUID> {
    private LocalDateTime dateReservation;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private Hebergement hebergement;
    private StatusReservation status;
    private int nbPersonnes;
    private int nombreEnfants;
    private UUID clientId;
    private BigDecimal montantLogement;


    public static Reservation create(UUID clientId, Hebergement hebergement, LocalDateTime dateReservation, LocalDate dateArrivee, LocalDate dateDepart, int nbPersonnes, int nbEnfants) {

        validateInputs(clientId, hebergement, dateReservation, dateArrivee, dateDepart, nbPersonnes, nbEnfants);

        Reservation reservation = new Reservation();

        reservation.id = UUID.randomUUID();
        reservation.clientId = clientId;
        reservation.hebergement = hebergement;
        reservation.dateReservation = dateReservation;
        reservation.dateArrivee = dateArrivee;
        reservation.dateDepart = dateDepart;
        reservation.nbPersonnes = nbPersonnes;
        reservation.nombreEnfants = nbEnfants;
        reservation.status = StatusReservation.EN_ATTENTE;

        reservation.validateBusinessRules();

        return reservation;
    }


    private static void validateInputs(UUID clientId, Hebergement hebergement, LocalDateTime dateReservation, LocalDate dateArrivee, LocalDate dateDepart, int nbPersonnes, int nbEnfants) {
        if (clientId == null) throw new BusinessException("Client obligatoire");
        if (hebergement == null) throw new BusinessException("Hébergement obligatoire");
        if (dateReservation == null) throw new BusinessException("Date réservation obligatoire");
        if (dateArrivee == null || dateDepart == null) throw new BusinessException("Dates obligatoires");
        if (dateArrivee.isAfter(dateDepart)) throw new BusinessException("Date arrivée doit être avant date départ");
        if (nbPersonnes <= 0 || nbEnfants < 0) throw new BusinessException("Occupants invalides");
    }


    private void validateBusinessRules() {
        validateOccupants();
        validateReservationDates();
    }


    private void validateOccupants() {
        if (nbPersonnes <= 0 || nombreEnfants < 0) {
            throw new BusinessException("Occupants invalides");
        }
    }

    private void validateReservationDates() {
        if (dateArrivee.isAfter(dateDepart)) {
            throw new BusinessException("La date d'arrivée doit etre avant la date de départ");
        }
    }

    public void terminer() {
        if (this.status == StatusReservation.ANULLEE) {
            throw new BusinessException("Impossible de terminer une réservation annulée");
        }
        this.status = StatusReservation.TERMINE;
    }

    public void confirmer() {
        if (status != StatusReservation.EN_ATTENTE) {
            throw new BusinessException("Impossible de confirmer cette reservation  " + this.status.name().toLowerCase());
        }
        this.status = StatusReservation.CONFIRME;
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

    public long getNbJours() {
        return Duration.between(dateArrivee, dateDepart).toDays();
    }

    public BigDecimal calculerCoutLogement() {
        this.montantLogement = hebergement.getPrixParNuit().multiply(BigDecimal.valueOf(getNbJours()));
        return montantLogement;
    }

    public void applyPromotion(PromotionStrategy promotionStrategy) {
        this.montantLogement = promotionStrategy.apply(montantLogement);
    }


    public BigDecimal getMontantLogement() {
        return montantLogement.setScale(2, RoundingMode.HALF_UP);
    }

    public boolean estConfirmee() {
        return status == StatusReservation.CONFIRME;
    }

}
