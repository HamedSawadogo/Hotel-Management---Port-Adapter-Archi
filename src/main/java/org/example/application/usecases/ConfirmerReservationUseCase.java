package org.example.application.usecases;

import lombok.RequiredArgsConstructor;
import org.example.application.dtos.PaymentRequest;
import org.example.application.ports.EventPublisher;
import org.example.application.ports.PaymentServicePort;
import org.example.application.ports.ReservationPort;
import org.example.application.ports.SejourPort;
import org.example.domain.models.BusinessException;
import org.example.domain.models.Reservation;
import org.example.domain.models.ReservationConfirmee;
import org.example.domain.services.PricingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmerReservationUseCase {
    private final ReservationPort reservationPort;
    private final SejourPort sejourPort;
    private final CreerFactureUseCase creerFactureUseCase;
    private final PricingService pricingService;
    private final PaymentServicePort paymentServicePort;
    private final EventPublisher eventPublisher;

    public void confirmer(Long reservationId, PaymentRequest paymentRequest) {
        final Reservation reservation = reservationPort.findById(reservationId);
        final var sejour = sejourPort.getIfExist(reservation);
        final BigDecimal montantTotal = pricingService.getReservationPrice(reservation, sejour);

        if (!acompteSuffisant(paymentRequest.amount(), montantTotal)) {
            throw new BusinessException(
                    "Le montant du paiement doit être supérieur ou égal à 30% du montant total (" + montantTotal + ")");
        }

        creerFactureUseCase.creer(reservation, montantTotal);
        reservation.confirmer();
        reservationPort.sauvegarder(reservation);
        paymentServicePort.payer(paymentRequest);

        eventPublisher.publish(new ReservationConfirmee(
                reservation.getId(),
                reservation.getClient().getId(),
                montantTotal));
    }


    private boolean acompteSuffisant(BigDecimal paiement, BigDecimal montantTotal) {
        BigDecimal seuilMinimum = montantTotal.multiply(new BigDecimal("0.30"));
        return paiement.compareTo(seuilMinimum) >= 0;
    }
}
