package org.example.application;

import lombok.RequiredArgsConstructor;
import org.example.application.dtos.InitReservationCommand;
import org.example.application.dtos.PaymentRequest;
import org.example.application.ports.in.ConfirmReservationUseCase;
import org.example.application.ports.in.InitReservationUseCase;
import org.example.domain.services.PromotionFactory;
import org.example.domain.services.PromotionStrategy;
import org.example.domain.events.ReservationCreatedEvent;
import org.example.domain.gateways.AuthenticatedUserGateway;
import org.example.domain.gateways.ClientPort;
import org.example.domain.models.*;
import org.example.domain.ports.out.eventpublisher.ReservationEventPublisher;
import org.example.domain.ports.out.repositories.HebergementPort;
import org.example.domain.ports.out.repositories.PaymentServicePort;
import org.example.domain.ports.out.repositories.ReservationPort;
import org.example.domain.ports.out.repositories.SejourPort;
import org.example.domain.services.PaymentPolicy;
import org.example.domain.services.PricingService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationApplicationService implements InitReservationUseCase, ConfirmReservationUseCase {
    private final ClientPort clientPort;
    private final ReservationPort reservationPort;
    private final HebergementPort hebergementPort;
    private final SejourPort sejourPort;
    private final PricingService pricingService;
    private final PaymentPolicy paymentPolicy;
    private final PaymentServicePort paymentServicePort;
    private final AuthenticatedUserGateway authenticatedUserGateway;
    private final ReservationEventPublisher reservationEventPublisher;

    @Override
    public Reservation init(InitReservationCommand command) {
        final String authUser = authenticatedUserGateway.getCurrentUser();
        Optional<Reservation> reservationExist = reservationPort.getReservationBetweenByHebergement(command.getDateArrivee(), command.getDateDepart(), command.getHebergementId());

        if (reservationExist.isPresent() && reservationExist.get().estConfirmee()) {
            throw new BusinessException("Une reservation en cours existe déja");
        }

        Hebergement hebergement = checkAndvalidateHebergement(command.getHebergementId());
        Reservation reservationInitiated = Reservation.create(UUID.fromString(command.getClientId().toString()), hebergement, command.getDateReservation(), command.getDateArrivee(), command.getDateDepart(), command.getNbPersonnes(), command.getNbEnfants());

        int userReservationCount = reservationPort.countAllByCurrentUse(authUser);
        final var client = clientPort.findById(command.getClientId());
        final PromotionStrategy promotionStrategy = PromotionFactory.resolve(userReservationCount, client.isEsFidel());
        reservationInitiated.applyPromotion(promotionStrategy);
        final var event = new  ReservationCreatedEvent(reservationInitiated);
        reservationEventPublisher.publish(event);
        return reservationPort.creer(reservationInitiated);
    }

    private Hebergement checkAndvalidateHebergement(Long hebergementId) {
        Optional<Hebergement> optionalHebergement = hebergementPort.findById(hebergementId);
        if (optionalHebergement.isEmpty()) {
            throw new BusinessException("henergement introuvable");
        }
        Hebergement hebergement = optionalHebergement.get();
        if (hebergement.getStatusHebergement() == StatusHebergement.INDISPONIBLE) {
            throw new BusinessException("Hebergement occupé");
        }
        return hebergement;
    }

    @Override
    public void confirmer(Long reservationId, PaymentRequest paymentRequest) {
        final Reservation reservation = reservationPort.findById(reservationId);
        final var sejour = sejourPort.getIfExist(reservation);

        final BigDecimal montantTotal = pricingService.getReservationPrice(reservation, sejour);
        final boolean validaterMontantMinimumPayment = paymentPolicy.validaterMontantMinimumPayment(paymentRequest.amount(), montantTotal);

        if (!validaterMontantMinimumPayment) {
            throw new BusinessException("vous devez payer au minimum 30% du montant total");
        }

        reservation.confirmer();

        reservationPort.sauvegarder(reservation);
        paymentServicePort.payer(paymentRequest);

        reservationEventPublisher.publish(new ReservationConfirmee(reservation));
    }
}
