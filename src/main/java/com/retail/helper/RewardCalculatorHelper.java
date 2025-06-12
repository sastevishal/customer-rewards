package com.retail.helper;
public class RewardCalculatorHelper {

    /**
     * Calculates reward points:
     * - 2 points for every dollar spent over $100,
     * - 1 point for every dollar spent between $50 and $100.
     */
    public static int calculateRewardPoints(double amount) {
        return amount > 100 ? (int) ((amount - 100) * 2 + 50) :
                amount > 50 ? (int) (amount - 50) :
                        0;
    }
}
