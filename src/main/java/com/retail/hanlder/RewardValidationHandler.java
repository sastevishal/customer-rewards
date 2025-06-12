package com.retail.hanlder;

import com.retail.entity.Customer;
import com.retail.exceptionhandler.NoCustomerFoundException;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class RewardValidationHandler {

    public void validateEndDate(LocalDate endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("End date must not be null.");
        }
    }

    public void validateCustomerList(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            throw new NoCustomerFoundException("No customers found in the system.");
        }
    }
}

