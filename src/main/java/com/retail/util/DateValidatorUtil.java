package com.retail.util;

import java.time.LocalDate;

public class DateValidatorUtil {

    public static void validateDates(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }
        if (start.isAfter(LocalDate.now()) || end.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Dates cannot be in the future.");
        }
    }
}
