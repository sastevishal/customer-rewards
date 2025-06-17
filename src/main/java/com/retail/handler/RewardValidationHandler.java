package com.retail.handler;

import com.retail.entity.Customer;
import com.retail.exceptionhandler.NoCustomerFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class RewardValidationHandler {

    public void validateDate(LocalDate date) {
        Optional.ofNullable(date)
                .orElseThrow(() -> new IllegalArgumentException("Date must not be null."));
    }

    public void validateCustomerList(List<Customer> customers) {
        Optional.ofNullable(customers)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NoCustomerFoundException("No customers found in the system."));
    }
}

