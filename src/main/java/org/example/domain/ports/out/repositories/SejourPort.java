package org.example.domain.ports.out.repositories;

import org.example.domain.models.Reservation;
import org.example.domain.models.Sejour;
import java.util.Optional;

public interface SejourPort {
    Sejour creer(Sejour sejour);
    Optional<Sejour> getIfExist(Reservation reservation);
    Optional<Sejour> findById(Long sejourId);
}
