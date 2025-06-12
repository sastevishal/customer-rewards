package com.retail.controller;

import com.retail.dto.CustomerRewardResponse;
import com.retail.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RewardControllerTest {

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRewardsForAllCustomers_shouldReturnRewardList() {
        // Arrange
        LocalDate endDate = LocalDate.of(2025, 6, 9);
        CustomerRewardResponse response1 = new CustomerRewardResponse();
        response1.setCustomerId(1L);
        response1.setCustomerName("Alice");
        response1.setTotalRewards(120);

        CustomerRewardResponse response2 = new CustomerRewardResponse();
        response2.setCustomerId(2L);
        response2.setCustomerName("Bob");
        response2.setTotalRewards(90);

        List<CustomerRewardResponse> mockResponse = Arrays.asList(response1, response2);
        when(rewardService.getAllCustomerRewards(endDate)).thenReturn(mockResponse);

        ResponseEntity<List<CustomerRewardResponse>> response = rewardController.getRewardsForAllCustomers(endDate);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Alice", response.getBody().get(0).getCustomerName());
        verify(rewardService, times(1)).getAllCustomerRewards(endDate);
    }
}
