package org.example.domain.models;

import lombok.Getter;

@Getter
public class Hebergement {
    private Long id;
    private String typeHebergement;
    private int prixParNuit;
    private StatusHebergement statusHebergement;

    public void liberer() {
        if (statusHebergement == StatusHebergement.INDISPONIBLE) {
            this.statusHebergement = StatusHebergement.DISPONIBLE;
        }
    }

    public void occuper() {
        if (this.statusHebergement == StatusHebergement.DISPONIBLE) {
            this.statusHebergement = StatusHebergement.INDISPONIBLE;
        }
    }
}
