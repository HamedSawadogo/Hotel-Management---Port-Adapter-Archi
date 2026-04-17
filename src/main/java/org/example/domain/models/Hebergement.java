package org.example.domain.models;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class Hebergement {
    private Long id;
    private String typeHebergement;
    private BigDecimal prixParNuit;
    private StatusHebergement statusHebergement;


    public Hebergement(String typeHebergement, BigDecimal prixParNuit) {
        this.typeHebergement = typeHebergement;
        this.prixParNuit = prixParNuit;
        this.statusHebergement = StatusHebergement.INDISPONIBLE;
    }

    public void liberer() {
        if (statusHebergement != StatusHebergement.INDISPONIBLE) {
            throw new BusinessException("L'hébergement n'est pas occupé");
        }
        this.statusHebergement = StatusHebergement.DISPONIBLE;
    }

    public void occuper() {
        if (statusHebergement != StatusHebergement.DISPONIBLE) {
            throw new BusinessException("L'hébergement n'est pas disponible");
        }
        this.statusHebergement = StatusHebergement.INDISPONIBLE;
    }
}
