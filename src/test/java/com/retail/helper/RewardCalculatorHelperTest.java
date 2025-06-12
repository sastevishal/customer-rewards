package com.retail.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RewardCalculatorHelperTest {

    @Test
    void calculateRewardPoints_whenAmountLessThan50_shouldReturnZero() {
        int points = RewardCalculatorHelper.calculateRewardPoints(45.0);
        assertEquals(0, points);
    }

    @Test
    void calculateRewardPoints_whenAmountExactly50_shouldReturnZero() {
        int points = RewardCalculatorHelper.calculateRewardPoints(50.0);
        assertEquals(0, points);
    }

    @Test
    void calculateRewardPoints_whenAmountBetween50And100_shouldReturnCorrectPoints() {
        int points = RewardCalculatorHelper.calculateRewardPoints(75.0);
        assertEquals(25, points);
    }

    @Test
    void calculateRewardPoints_whenAmountExactly100_shouldReturn50Points() {
        int points = RewardCalculatorHelper.calculateRewardPoints(100.0);
        assertEquals(50, points);
    }

    @Test
    void calculateRewardPoints_whenAmountAbove100_shouldReturnCorrectPoints() {
        int points = RewardCalculatorHelper.calculateRewardPoints(120.0);
        assertEquals(90, points);
    }

    @Test
    void calculateRewardPoints_whenAmountIsLarge_shouldReturnCorrectPoints() {
        int points = RewardCalculatorHelper.calculateRewardPoints(250.0);
        assertEquals(350, points);
    }
}
