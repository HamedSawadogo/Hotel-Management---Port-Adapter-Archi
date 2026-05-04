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
        if (statusHebergement == StatusHebergement.INDISPONIBLE) {
            this.statusHebergement = StatusHebergement.DISPONIBLE;
        } else {
            throw new BusinessException("L'hébergement n'est pas occupé");
        }
    }

    public void occuper() {
        if (statusHebergement != StatusHebergement.DISPONIBLE) {
            throw new BusinessException("L'hébergement n'est plus disponible");
        }
        this.statusHebergement = StatusHebergement.INDISPONIBLE;
    }
}
