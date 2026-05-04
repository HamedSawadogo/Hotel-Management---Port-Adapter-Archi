package domain.usecases;

import org.example.domain.gateways.AuthenticatedUserGateway;
import org.example.domain.gateways.ClientPort;
import org.example.domain.models.*;
import org.example.domain.ports.out.repositories.HebergementPort;
import org.example.domain.ports.out.repositories.ReservationPort;
import org.example.domain.ports.out.repositories.SejourPort;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class CreateReservationUseCaseUnitTest {

    @InjectMocks
    CreateReservationUseCase createReservationUseCase;

    @Mock
    HebergementPort hebergementPort;

    @Mock
    ClientPort clientPort;

    @Mock
    SejourPort sejourPort;

    @Mock
    ReservationPort reservationPort;

    @Mock
    AuthenticatedUserGateway authenticatedUserGateway;

    private static final String CURRENT_USER = "Hamed";

    Client client = new Client();
    Hebergement hebergement = new Hebergement("Duplex", new BigDecimal("15000"));

    @Test
    void should_create_when_create_new_reservation_without_promotion() {
        final LocalDateTime reservationDate = LocalDateTime.now();
        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = LocalDate.now();
        final Long hebergementId = 1L;
        final Long clientId = 1L;

        when(authenticatedUserGateway.getCurrentUser()).thenReturn(CURRENT_USER);
        when(clientPort.findById(clientId)).thenReturn(client);
        when(hebergementPort.findById(hebergementId)).thenReturn(hebergement);
        when(reservationPort.countAllByCurrentUse(CURRENT_USER)).thenReturn(10);
        when(reservationPort.creer(any())).thenReturn(new Reservation(
                reservationDate,
                startDate,
                endDate,
                hebergement,
                1,
                0,
                client
        ));

        createReservationUseCase = new CreateReservationUseCase(
                hebergementPort,
                clientPort,
                sejourPort,
                reservationPort,
                authenticatedUserGateway
        );

        Reservation reservationCreated = createReservationUseCase.creerReservation(
                reservationDate,
                startDate,
                endDate,
                null,
                hebergementId,
                clientId,
                1,
                0
        );

        assertThat(reservationCreated).isNotNull();
        assertThat(reservationCreated.getDateReservation()).isEqualTo(reservationDate);
        assertThat(reservationCreated.getStatus()).isEqualTo(StatusReservation.EN_ATTENTE);
    }
}
