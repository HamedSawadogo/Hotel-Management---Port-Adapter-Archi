package org.example.application.usecases;

import org.example.domain.models.Reservation;
import org.example.domain.models.Sejour;
import org.example.domain.models.ServiceHebergement;
import org.example.application.ports.ClientPort;
import org.example.application.ports.HebergementPort;
import org.example.application.ports.ReservationPort;
import org.example.application.ports.SejourPort;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CreateReservationUseCase {
    private final HebergementPort hebergementPort;
    private final ClientPort clientPort;
    private final SejourPort sejourPort;
    private final ReservationPort reservationPort;

    public CreateReservationUseCase(HebergementPort hebergementPort,
                                    ClientPort clientPort,
                                    SejourPort sejourPort,
                                    ReservationPort reservationPort) {
        this.hebergementPort = hebergementPort;
        this.clientPort = clientPort;
        this.sejourPort = sejourPort;
        this.reservationPort = reservationPort;
    }


    public Reservation creerReservation(LocalDateTime dateReservation,
                                        LocalDate dateArrivee,
                                        LocalDate dateDepart,
                                        List<ServiceHebergement> services,
                                        Long hebergementId,
                                        Long clientId,
                                        int nbPersonnes,
                                        int nbEnfants) {
        final var hebergement = hebergementPort.findById(hebergementId);
        final var client = clientPort.findById(clientId);

        final var reservation = new Reservation(
                dateReservation,
                dateArrivee,
                dateDepart,
                hebergement,
                nbPersonnes,
                nbEnfants,
                client
        );

        if (!services.isEmpty()) {
            final var sejour = new Sejour(reservation);
            sejour.addServices(services);
            sejourPort.creer(sejour);
        }

        return reservationPort.creer(reservation);
    }
}
