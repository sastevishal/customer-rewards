package com.retail.service;

import com.retail.dto.CustomerRewardResponse;
import com.retail.entity.Customer;
import com.retail.entity.Transaction;
import com.retail.helper.CustomerRepositoryHelper;
import com.retail.helper.TransactionRepositoryHelper;
import com.retail.util.RewardValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RewardServiceImplTest {

    private CustomerRepositoryHelper customerRepoHelper;
    private TransactionRepositoryHelper transactionRepoHelper;
    private RewardValidationUtil rewardValidationUtil;
    private RewardServiceImpl rewardService;

    @BeforeEach
    void setUp() {
        customerRepoHelper = mock(CustomerRepositoryHelper.class);
        transactionRepoHelper = mock(TransactionRepositoryHelper.class);
        rewardValidationUtil = mock(RewardValidationUtil.class);
        rewardService = new RewardServiceImpl(customerRepoHelper, rewardValidationUtil, transactionRepoHelper);
    }

    @Test
    @DisplayName("Should calculate rewards for all customers correctly")
    void testGetAllCustomerRewards() {

        LocalDate start = LocalDate.of(2024, 3, 1);
        LocalDate end = LocalDate.of(2024, 6, 1);

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("John");

        Transaction tx1 = new Transaction();
        tx1.setTransactionId(101L);
        tx1.setTransactionAmount(120.0);
        tx1.setTransactionDate(LocalDate.of(2024, 3, 10));

        when(customerRepoHelper.fetchAllCustomers()).thenReturn(List.of(customer));
        when(transactionRepoHelper.findByCustomerIdAndTransactionDate(1L, start, end))
                .thenReturn(List.of(tx1));

        List<CustomerRewardResponse> responses = rewardService.getAllCustomerRewards(start, end);

        assertEquals(1, responses.size());
        CustomerRewardResponse response = responses.get(0);
        assertEquals(1L, response.getCustomerId());
        assertEquals("John", response.getCustomerName());
        assertEquals(90, response.getTotalRewards()); // 120 => 50 between 50-100 + 20 * 2
        assertEquals(1, response.getMonthlyRewards().size());
        assertTrue(response.getMonthlyRewards().containsKey("MARCH"));
    }

    @Test
    @DisplayName("Should calculate reward for specific customer correctly")
    void testGetCustomerRewardById() {
        LocalDate start = LocalDate.of(2024, 3, 1);
        LocalDate end = LocalDate.of(2024, 6, 1);

        Customer customer = new Customer();
        customer.setCustomerId(2L);
        customer.setCustomerName("Jane");

        Transaction tx1 = new Transaction();
        tx1.setTransactionId(102L);
        tx1.setTransactionAmount(95.0);
        tx1.setTransactionDate(LocalDate.of(2024, 4, 15));

        when(customerRepoHelper.fetchCustomerById(2L)).thenReturn(customer);
        when(transactionRepoHelper.findByCustomerIdAndTransactionDate(2L, start, end))
                .thenReturn(List.of(tx1));

        CustomerRewardResponse response = rewardService.getCustomerRewardById(2L, start, end);

        assertNotNull(response);
        assertEquals(2L, response.getCustomerId());
        assertEquals("Jane", response.getCustomerName());
        assertEquals(45, response.getTotalRewards()); // 95 => 45 points (45 between 50-100)
        assertEquals(1, response.getMonthlyRewards().size());
        assertTrue(response.getMonthlyRewards().containsKey("APRIL"));
    }

    @Test
    @DisplayName("Should handle empty customers and return empty list")
    void testGetAllCustomerRewardsWithNoCustomers() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 2, 1);

        when(customerRepoHelper.fetchAllCustomers()).thenReturn(Collections.emptyList());

        List<CustomerRewardResponse> result = rewardService.getAllCustomerRewards(start, end);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should handle no transactions for specific customer")
    void testGetCustomerRewardByIdWithNoTransactions() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 2, 1);

        Customer customer = new Customer();
        customer.setCustomerId(3L);
        customer.setCustomerName("Ravi");

        when(customerRepoHelper.fetchCustomerById(3L)).thenReturn(customer);
        when(transactionRepoHelper.findByCustomerIdAndTransactionDate(3L, start, end))
                .thenReturn(Collections.emptyList());

        CustomerRewardResponse result = rewardService.getCustomerRewardById(3L, start, end);

        assertEquals(0, result.getTotalRewards());
        assertTrue(result.getTransactions().isEmpty());
        assertTrue(result.getMonthlyRewards().isEmpty());
    }
}
