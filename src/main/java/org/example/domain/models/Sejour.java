package org.example.domain.models;

import lombok.Getter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Sejour {
    private Long id;
    private Reservation reservation;
    private Client client;
    private Hebergement hebergement;
    private List<ServiceHebergement> serviceHebergements;
    private BigDecimal montant;

    public Sejour(Reservation reservation) {
        this.reservation = reservation;
        this.client = reservation.getClient();
        this.hebergement = reservation.getHebergement();
        this.serviceHebergements = new ArrayList<>();
        this.montant = BigDecimal.ZERO;
    }

    public void addService(ServiceHebergement service) {
        this.serviceHebergements.add(service);
        this.montant = this.montant.add(service.getPrix());
    }

    public void addServices(List<ServiceHebergement> services) {
        services.forEach(this::addService);
    }
}
