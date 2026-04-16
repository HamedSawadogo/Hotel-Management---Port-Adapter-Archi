package org.example.application.usecases;

import org.example.domain.models.Reservation;
import org.example.domain.models.Sejour;
import org.example.domain.models.ServiceHebergemet;
import org.example.domain.ports.ClientPort;
import org.example.domain.ports.HebergementPort;
import org.example.domain.ports.ReservationPort;
import org.example.domain.ports.SejourPort;
import java.time.LocalDateTime;
import java.util.List;

public class CreateReservationUseCase {
    private final HebergementPort hebergementPort;
    private final ClientPort clientPort;
    private final SejourPort sejourPort;
    private final ReservationPort reservationPort;

    public CreateReservationUseCase(
            HebergementPort hebergementPort,
            ClientPort clientPort, SejourPort sejourPort,
            ReservationPort reservationPort
    ) {
        this.hebergementPort = hebergementPort;
        this.clientPort = clientPort;
        this.sejourPort = sejourPort;
        this.reservationPort = reservationPort;
    }

    public void creerReservation(
            LocalDateTime dateReservation,
            List<ServiceHebergemet> serviceHebergemets,
            Long herbergementId,
            Long clientId,
            int nbPersonnes,
            int nbJours,
            int nbEnfants

    ) {
        final var hebergement = hebergementPort.getOneById(herbergementId);
        final var client = clientPort.getOneById(clientId);

        final Reservation reservation = new Reservation(
                dateReservation,
                hebergement,
                nbPersonnes,
                nbJours,
                nbEnfants,
                client
        );
        if (!serviceHebergemets.isEmpty()) {
            final Sejour sejour = new Sejour(reservation);
            sejour.addServices(serviceHebergemets);
            sejourPort.creer(sejour);
        }
        reservationPort.creer(reservation);
    }
}