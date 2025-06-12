package com.retail.hanlder;

import com.retail.dto.CustomerRewardResponse;
import com.retail.entity.Customer;
import com.retail.entity.Transaction;
import com.retail.exceptionhandler.NoTransactionFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RewardDataHandlerTest {

    private RewardDataHandler rewardDataHandler;

    @BeforeEach
    void setup() {
        rewardDataHandler = new RewardDataHandler();
    }

    @Test
    void shouldReturnValidRewards_whenTransactionsAreWithinRange() {
        // Setup
        LocalDate endDate = LocalDate.of(2025, 6, 1);
        LocalDate txDate = endDate.minusMonths(1);

        Transaction tx1 = new Transaction(1L, 120.0, txDate, null);
        Transaction tx2 = new Transaction(2L, 75.0, txDate, null);

        Customer customer = new Customer();
        customer.setCustomerId(101L);
        customer.setCustomerName("Alice");
        customer.setTransactions(Arrays.asList(tx1, tx2));

        List<Customer> customerList = List.of(customer);

        // Execute
        List<CustomerRewardResponse> response = rewardDataHandler.buildCustomerRewardResponses(
                customerList,
                endDate.minusMonths(3),
                endDate
        );

        // Assert
        assertEquals(1, response.size());
        CustomerRewardResponse result = response.get(0);
        assertEquals("Alice", result.getCustomerName());
        assertEquals(101L, result.getCustomerId());
        assertTrue(result.getTotalRewards() > 0);
        assertFalse(result.getMonthlyRewards().isEmpty());
    }

    @Test
    void shouldThrowException_whenCustomerHasNoTransactions() {
        // Setup
        LocalDate endDate = LocalDate.of(2025, 6, 1);

        Customer customer = new Customer();
        customer.setCustomerId(102L);
        customer.setCustomerName("Bob");
        customer.setTransactions(Collections.emptyList());

        List<Customer> customerList = List.of(customer);

        // Execute & Assert
        Exception exception = assertThrows(NoTransactionFoundException.class, () ->
                rewardDataHandler.buildCustomerRewardResponses(
                        customerList,
                        endDate.minusMonths(3),
                        endDate
                )
        );

        assertEquals("No transactions found for any customer in the last 3 months.", exception.getMessage());
    }

    @Test
    void shouldThrowException_whenAllTransactionsAreOutOfRange() {
        // Setup
        LocalDate endDate = LocalDate.of(2025, 6, 1);
        LocalDate oldDate = endDate.minusMonths(6);

        Transaction oldTx = new Transaction(3L, 100.0, oldDate, null);

        Customer customer = new Customer();
        customer.setCustomerId(103L);
        customer.setCustomerName("Charlie");
        customer.setTransactions(List.of(oldTx));

        List<Customer> customerList = List.of(customer);

        // Execute & Assert
        assertThrows(NoTransactionFoundException.class, () ->
                rewardDataHandler.buildCustomerRewardResponses(
                        customerList,
                        endDate.minusMonths(3),
                        endDate
                )
        );
    }
}
