package org.example.application.usecases;

import org.example.domain.PromotionFactory;
import org.example.domain.PromotionStrategy;
import org.example.domain.gateways.AuthenticatedUserGateway;
import org.example.domain.gateways.ClientPort;
import org.example.domain.models.Reservation;
import org.example.domain.models.Sejour;
import org.example.domain.models.ServiceHebergement;
import org.example.domain.repositories.HebergementPort;
import org.example.domain.repositories.ReservationPort;
import org.example.domain.repositories.SejourPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CreateReservationUseCase {
    private final HebergementPort hebergementPort;
    private final ClientPort clientPort;
    private final SejourPort sejourPort;
    private final ReservationPort reservationPort;
    private final AuthenticatedUserGateway authenticatedUserGateway;

    public CreateReservationUseCase(HebergementPort hebergementPort,
                                    ClientPort clientPort,
                                    SejourPort sejourPort,
                                    ReservationPort reservationPort,
                                    AuthenticatedUserGateway authenticatedUserGateway) {
        this.hebergementPort = hebergementPort;
        this.clientPort = clientPort;
        this.sejourPort = sejourPort;
        this.reservationPort = reservationPort;
        this.authenticatedUserGateway = authenticatedUserGateway;
    }


    public Reservation creerReservation(LocalDateTime dateReservation,
                                        LocalDate dateArrivee,
                                        LocalDate dateDepart,
                                        List<ServiceHebergement> services,
                                        Long hebergementId,
                                        Long clientId,
                                        int nbPersonnes,
                                        int nbEnfants
    ) {
        final String authUser = authenticatedUserGateway.getCurrentUser();
        int userReservationCount = reservationPort.countAllByCurrentUse(authUser);
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
        final PromotionStrategy promotionStrategy = PromotionFactory.resolve(userReservationCount, client.isEsFidel());
        reservation.aplyPromotion(promotionStrategy);

        if (!services.isEmpty()) {
            final var sejour = new Sejour(reservation);
            sejour.addServices(services);
            sejourPort.creer(sejour);
        }

        return reservationPort.creer(reservation);
    }
}
