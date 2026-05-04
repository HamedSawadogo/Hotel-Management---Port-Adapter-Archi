package domain;

import org.example.domain.services.PromotionByConsomation;
import org.example.domain.models.Hebergement;
import org.example.domain.models.Reservation;
import org.example.domain.models.StatusHebergement;
import org.example.domain.models.StatusReservation;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.assertj.core.api.Assertions.assertThat;


public class ReservationUnitTests {

    Hebergement hebergement = new Hebergement("Maison simple", new BigDecimal("1000.00"));

    public static Reservation createReservation() {
       return new Reservation(
                LocalDateTime.of(LocalDate.of(2026, 4, 17), LocalTime.of(10, 0)),
                LocalDate.of(2026, 4, 17),
                LocalDate.of(2026, 4, 19),
                 new Hebergement("Maison simple", new BigDecimal("1000.00")),
                2,
                0,
                null
        );
    }

    @Test
    void test_when_create_newReservationShouldPassed() {
        Reservation reservation = createReservation();
        assertThat(reservation.getNbJours()).isEqualTo(2);
        assertThat(reservation.getDateReservation()).isNotNull();
        assertThat(reservation.getDateArrivee()).isEqualTo(LocalDate.of(2026, 4, 17));
        assertThat(reservation.getDateDepart()).isEqualTo(LocalDate.of(2026, 4, 19));
        assertThat(reservation.getNbJours()).isEqualTo(2);
        assertThat(reservation.getStatus()).isEqualTo(StatusReservation.EN_ATTENTE);
    }

    @Test
    void test_when_cornfirm_ShouldPassed() {
        var reservation = createReservation();
        reservation.getHebergement().liberer();
        reservation.confirmer();
        assertThat(reservation.getStatus()).isEqualTo(StatusReservation.CONFIRME);
    }


    @Test
    void when_create_reservation_with_promotion_should_pased() {
        var reservation = createReservation();
        assertThat(reservation.getMontantLogement()).isEqualTo(new BigDecimal("2000.00"));
        reservation.applyPromotion(new PromotionByConsomation());
        assertThat(reservation.getMontantLogement()).isEqualTo(new BigDecimal("1800.00"));
    }


    @Test
    void when_anulate_reservation_sould_passed() {
        Reservation reservation =  createReservation();
        reservation.annuler();
        assertThat(reservation.getStatus()).isEqualTo(StatusReservation.ANULLEE);
    }


    @Test
    void when_terminate_reservation_sould_passed() {
        Reservation reservation = createReservation();
        reservation.terminer();
        assertThat(reservation.getStatus()).isEqualTo(StatusReservation.TERMINE);
        assertThat(reservation.getHebergement().getStatusHebergement()).isEqualTo(StatusHebergement.DISPONIBLE);
    }

}
