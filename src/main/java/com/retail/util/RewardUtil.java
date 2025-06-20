package com.retail.util;

public class RewardUtil {

    /**
     * Calculates reward points:
     * - 2 points for every dollar spent over $100,
     * - 1 point for every dollar spent between $50 and $100.
     */
    public static int calculateRewardPoints(double amount) {
        if (amount > 100) {
            return (int) Math.round(2 * (amount - 100) + 50);
        } else if (amount > 50) {
            return (int) Math.round(amount - 50);
        } else {
            return 0;
        }
    }
}
