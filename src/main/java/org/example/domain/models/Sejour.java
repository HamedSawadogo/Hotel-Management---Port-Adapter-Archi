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
    private List<ServiceHebergemet> serviceHebergemets;
    private BigDecimal motant;

    public Sejour(Reservation reservation) {
        this.reservation = reservation;
        this.client = reservation.getClient();
        this.hebergement = reservation.getHebergement();
        this.serviceHebergemets = new ArrayList<>();
    }

    public void addService(ServiceHebergemet serviceHebergemet) {
        this.serviceHebergemets.add(serviceHebergemet);
        this.motant = this.motant.add(serviceHebergemet.getPrix());
    }

    public void addServices(List<ServiceHebergemet> serviceHebergemets) {
        this.serviceHebergemets.addAll(serviceHebergemets);
        this.motant = this.motant.add(serviceHebergemets.stream().map(ServiceHebergemet::getPrix).reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
