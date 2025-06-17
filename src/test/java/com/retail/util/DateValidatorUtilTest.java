package com.retail.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateValidatorUtilTest {

    @Test
    void testValidDates() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 6, 1);
        assertDoesNotThrow(() -> DateValidatorUtil.validateDates(start, end));
    }

    @Test
    void testStartAfterEnd() {
        LocalDate start = LocalDate.of(2024, 7, 1);
        LocalDate end = LocalDate.of(2024, 6, 1);
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> DateValidatorUtil.validateDates(start, end));
        assertEquals("Start date must be before or equal to end date.", ex.getMessage());
    }

    @Test
    void testFutureDates() {
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = LocalDate.now().plusDays(5);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> DateValidatorUtil.validateDates(start, end));
        assertEquals("Dates cannot be in the future.", ex.getMessage());
    }
}
