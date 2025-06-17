package com.retail.handler;

import com.retail.entity.Customer;
import com.retail.exceptionhandler.NoCustomerFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RewardValidationHandlerTest {

    private RewardValidationHandler validationHandler;

    @BeforeEach
    void setUp() {
        validationHandler = new RewardValidationHandler();
    }

    @Test
    void validateEndDate_shouldNotThrowForValidDate() {
        assertDoesNotThrow(() -> validationHandler.validateDate(LocalDate.now()));
    }

    @Test
    void validateEndDate_shouldThrowForNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                validationHandler.validateDate(null)
        );
        assertEquals("End date must not be null.", exception.getMessage());
    }

    @Test
    void validateCustomerList_shouldNotThrowForNonEmptyList() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("John");
        List<Customer> customerList = List.of(customer);

        assertDoesNotThrow(() -> validationHandler.validateCustomerList(customerList));
    }

    @Test
    void validateCustomerList_shouldThrowForEmptyList() {
        Exception exception = assertThrows(NoCustomerFoundException.class, () ->
                validationHandler.validateCustomerList(Collections.emptyList())
        );
        assertEquals("No customers found in the system.", exception.getMessage());
    }

    @Test
    void validateCustomerList_shouldThrowForNullList() {
        Exception exception = assertThrows(NoCustomerFoundException.class, () ->
                validationHandler.validateCustomerList(null)
        );
        assertEquals("No customers found in the system.", exception.getMessage());
    }
}
