package com.retail.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardUtilTest {

    @Test
    void calculateRewardPoints_whenAmountLessThan50_shouldReturnZero() {
        int points = RewardUtil.calculateRewardPoints(45.0);
        assertEquals(0, points);
    }

    @Test
    void calculateRewardPoints_whenAmountExactly50_shouldReturnZero() {
        int points = RewardUtil.calculateRewardPoints(50.0);
        assertEquals(0, points);
    }

    @Test
    void calculateRewardPoints_whenAmountBetween50And100_shouldReturnCorrectPoints() {
        int points = RewardUtil.calculateRewardPoints(75.0);
        assertEquals(25, points);
    }

    @Test
    void calculateRewardPoints_whenAmountExactly100_shouldReturn50Points() {
        int points = RewardUtil.calculateRewardPoints(100.0);
        assertEquals(50, points);
    }

    @Test
    void calculateRewardPoints_whenAmountAbove100_shouldReturnCorrectPoints() {
        int points = RewardUtil.calculateRewardPoints(120.0);
        assertEquals(90, points);
    }

    @Test
    void calculateRewardPoints_whenAmountIsLarge_shouldReturnCorrectPoints() {
        int points = RewardUtil.calculateRewardPoints(250.0);
        assertEquals(350, points);
    }
}
