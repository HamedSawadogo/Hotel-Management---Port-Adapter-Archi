package org.example.domain.services;

public class PromotionFactory {
    public static PromotionStrategy resolve(int userReservationCount, boolean esFidel) {
        if (userReservationCount >= 5) {
            return new PromotionByConsomation();
        }
        return new PromotionByFidelisation();
    }
}
