package com.retail.service;

import com.retail.dto.CustomerRewardResponse;
import com.retail.entity.Customer;
import com.retail.entity.Transaction;
import com.retail.handler.RewardValidationHandler;
import com.retail.helper.CustomerRepositoryHelper;
import com.retail.helper.TransactionRepositoryHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardServiceImplTest {

    @Mock
    private CustomerRepositoryHelper customerRepositoryHelper;

    @Mock
    private RewardValidationHandler rewardValidationHandler;

    @Mock
    private TransactionRepositoryHelper transactionRepositoryHelper;

    @InjectMocks
    private RewardServiceImpl rewardService;

    private Customer customer;
    private List<Transaction> transactions;

    @BeforeEach
    void setup() {
        customer = new Customer(1L, "John Doe");
        transactions = Arrays.asList(
                new Transaction(1L, 120.0, LocalDate.now().minusDays(10), customer),
                new Transaction(2L, 70.0, LocalDate.now().minusDays(20), customer)
        );
    }

    @Test
    void testGetAllCustomerRewards_ShouldReturnRewards() {
        LocalDate startDate = LocalDate.now().minusMonths(3);
        LocalDate endDate = LocalDate.now();

        when(customerRepositoryHelper.fetchAllCustomers()).thenReturn(Collections.singletonList(customer));
        when(transactionRepositoryHelper.findByCustomerIdAndTransactionDate(1L, startDate, endDate)).thenReturn(transactions);

        List<CustomerRewardResponse> responses = rewardService.getAllCustomerRewards(startDate, endDate);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getCustomerId());
        assertEquals("John Doe", responses.get(0).getCustomerName());
        assertTrue(responses.get(0).getTotalRewards() > 0);
    }

    @Test
    void testGetCustomerRewardById_ShouldReturnReward() {
        LocalDate startDate = LocalDate.now().minusMonths(3);
        LocalDate endDate = LocalDate.now();

        when(customerRepositoryHelper.fetchCustomerById(1L)).thenReturn(customer);
        when(transactionRepositoryHelper.findByCustomerIdAndTransactionDate(1L, startDate, endDate)).thenReturn(transactions);

        CustomerRewardResponse response = rewardService.getCustomerRewardById(1L, startDate, endDate);

        assertNotNull(response);
        assertEquals(1L, response.getCustomerId());
        assertEquals("John Doe", response.getCustomerName());
        assertTrue(response.getTotalRewards() > 0);
    }

    @Test
    void testGetAllCustomerRewards_WithNoCustomers_ShouldThrowException() {
        LocalDate startDate = LocalDate.now().minusMonths(3);
        LocalDate endDate = LocalDate.now();

        when(customerRepositoryHelper.fetchAllCustomers()).thenReturn(Collections.emptyList());
        doThrow(new RuntimeException("No customers found"))
                .when(rewardValidationHandler).validateCustomerList(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () ->
                rewardService.getAllCustomerRewards(startDate, endDate));

        assertEquals("No customers found", exception.getMessage());
    }

    @Test
    void testGetCustomerRewardById_InvalidDates_ShouldThrowException() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusMonths(1);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                rewardService.getCustomerRewardById(1L, startDate, endDate));

        assertEquals("Start date cannot be after end date", exception.getMessage());
    }
}
