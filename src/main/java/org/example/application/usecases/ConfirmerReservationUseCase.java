package org.example.application.usecases;

import org.example.domain.PricingService;
import org.example.domain.models.Reservation;
import org.example.domain.models.ReservationConfirmee;
import org.example.domain.models.Sejour;
import org.example.domain.ports.EventPublisher;
import org.example.domain.ports.ReservationPort;
import org.example.domain.ports.SejourPort;


public class ConfirmerReservationUseCase {
    private final ReservationPort reservationPort;
    private final SejourPort sejourPort;
    private final CreerFactureUseCase creerFactureUseCase;
    private final PricingService pricingService;
    private final EventPublisher eventPublisher;

    public ConfirmerReservationUseCase(ReservationPort reservationPort,
                                       SejourPort sejourPort,
                                       CreerFactureUseCase creerFactureUseCase,
                                       PricingService pricingService,
                                       EventPublisher eventPublisher) {
        this.reservationPort = reservationPort;
        this.sejourPort = sejourPort;
        this.creerFactureUseCase = creerFactureUseCase;
        this.pricingService = pricingService;
        this.eventPublisher = eventPublisher;
    }

    public void confirmer(Long reservationId) {
        final Reservation reservation = reservationPort.findOneById(reservationId);
        reservation.confirmer();
        final Sejour sejour = new Sejour(reservation);
        sejourPort.creer(sejour);
        creerFactureUseCase.creer(reservation, pricingService.getReservationPrice(reservation));
        eventPublisher.publish(new ReservationConfirmee());
    }

}
