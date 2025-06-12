package com.retail.service;

import com.retail.dto.CustomerRewardResponse;
import com.retail.entity.Customer;
import com.retail.hanlder.RewardDataHandler;
import com.retail.hanlder.RewardValidationHandler;
import com.retail.helper.CustomerRepositoryHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewardServiceImplTest {

    @Mock
    private RewardDataHandler rewardDataHandler;

    @Mock
    private CustomerRepositoryHelper customerRepositoryHelper;

    @Mock
    private RewardValidationHandler rewardValidationHandler;

    @InjectMocks
    private RewardServiceImpl rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomerRewards_shouldReturnRewards() {
        // Arrange
        LocalDate endDate = LocalDate.of(2025, 6, 9);
        LocalDate startDate = endDate.minusMonths(3);

        Customer customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setCustomerName("Alice");

        List<Customer> customerList = List.of(customer1);

        CustomerRewardResponse response = new CustomerRewardResponse();
        response.setCustomerId(1L);
        response.setCustomerName("Alice");
        response.setTotalRewards(100);

        List<CustomerRewardResponse> rewardList = List.of(response);

        when(customerRepositoryHelper.fetchAllCustomers()).thenReturn(customerList);
        when(rewardDataHandler.buildCustomerRewardResponses(customerList, startDate, endDate)).thenReturn(rewardList);

        List<CustomerRewardResponse> result = rewardService.getAllCustomerRewards(endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getCustomerName());
        assertEquals(100, result.get(0).getTotalRewards());

        verify(rewardValidationHandler).validateEndDate(endDate);
        verify(customerRepositoryHelper).fetchAllCustomers();
        verify(rewardValidationHandler).validateCustomerList(customerList);
        verify(rewardDataHandler).buildCustomerRewardResponses(customerList, startDate, endDate);
    }

    @Test
    void getAllCustomerRewards_shouldThrowExceptionForNullEndDate() {
        // Arrange
        LocalDate nullEndDate = null;
        doThrow(new IllegalArgumentException("End date must not be null."))
                .when(rewardValidationHandler).validateEndDate(nullEndDate);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                rewardService.getAllCustomerRewards(nullEndDate)
        );
        assertEquals("End date must not be null.", exception.getMessage());
        verify(rewardValidationHandler).validateEndDate(nullEndDate);
        verifyNoInteractions(customerRepositoryHelper, rewardDataHandler);
    }
}
