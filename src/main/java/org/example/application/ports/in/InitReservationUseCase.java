package org.example.application.ports.in;

import org.example.application.dtos.InitReservationCommand;
import org.example.domain.models.Reservation;

public interface InitReservationUseCase {
     Reservation init(InitReservationCommand command);
}
