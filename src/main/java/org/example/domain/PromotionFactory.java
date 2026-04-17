package org.example.domain;

public class PromotionFactory {
    public static PromotionStrategy resolve(int userReservationCount, boolean esFidel) {
        if (userReservationCount >= 5) {
            return new PromotionByConsomation();
        }
        return new PromotionByFidelisation();
    }
}
