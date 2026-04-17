package domain;

import org.example.domain.PromotionByConsomation;
import org.example.domain.models.Hebergement;
import org.example.domain.models.Reservation;
import org.example.domain.models.StatusReservation;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.assertj.core.api.Assertions.assertThat;
public class ReservationUnitTests {


    Hebergement hebergement = new Hebergement("Maison simple", new BigDecimal("1000.00"));

    Reservation reservation = new Reservation(
            LocalDateTime.of(LocalDate.of(2026, 4, 17), LocalTime.of(10, 0)),
            LocalDate.of(2026, 4, 17),
            LocalDate.of(2026, 4, 19),
            hebergement,
            2,
            0,
            null
    );

    @Test
    void test_when_create_newReservationShouldPassed() {
        assertThat(reservation.getNbJours()).isEqualTo(2);
        assertThat(reservation.getStatus()).isEqualTo(StatusReservation.EN_ATTENTE);
    }

    @Test
    void test_when_cornfirm_ShouldPassed() {
        reservation.confirmer();
        assertThat(reservation.getStatus()).isEqualTo(StatusReservation.CONFIRME);
    }

    @Test
    void when_create_reservation_with_promotion_should_pased() {
        int nombreNuiteeClient = 5;
        boolean clientFidel = false;

        assertThat(reservation.getMontantLogement()).isEqualTo(new BigDecimal("2000.00"));
        reservation.aplyPromotion(new PromotionByConsomation());

        assertThat(reservation.getMontantLogement()).isEqualTo(new BigDecimal("1800.00"));


    }
}
