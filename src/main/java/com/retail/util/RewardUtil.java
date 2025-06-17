package com.retail.util;

public class RewardUtil {

    /**
     * Calculates reward points:
     * - 2 points for every dollar spent over $100,
     * - 1 point for every dollar spent between $50 and $100.
     */
    public static int calculateRewardPoints(double amount) {
        int intAmount = (int) Math.floor(amount);
        int over100 = Math.max(0, intAmount - 100);
        int between50And100 = Math.max(0, Math.min(intAmount, 100) - 50);
        return over100 * 2 + between50And100;
    }
}
