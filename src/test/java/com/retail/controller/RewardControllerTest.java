package com.retail.controller;

import com.retail.dto.CustomerRewardResponse;
import com.retail.dto.TransactionResponse;
import com.retail.service.RewardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Test
    @DisplayName("Should return rewards for all customers")
    void shouldReturnAllCustomerRewards() throws Exception {
        List<TransactionResponse> johnTransactions = asList(
                new TransactionResponse(101L, 120.75, LocalDate.of(2024, 3, 15)),
                new TransactionResponse(102L, 90.50, LocalDate.of(2024, 4, 10))
        );

        List<TransactionResponse> janeTransactions = List.of(
                new TransactionResponse(103L, 70.00, LocalDate.of(2024, 3, 20))
        );

        List<CustomerRewardResponse> mockResponse = asList(
                new CustomerRewardResponse(1L, "John", Map.of("MARCH", 70, "APRIL", 50), 120, johnTransactions),
                new CustomerRewardResponse(2L, "Jane", Map.of("MARCH", 80), 80, janeTransactions)
        );

        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 1);

        when(rewardService.getAllCustomerRewards(startDate, endDate)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/rewards")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].customerName").value("John"))
                .andExpect(jsonPath("$[0].totalRewards").value(120))
                .andExpect(jsonPath("$[0].transactions.size()").value(2));
    }

    @Test
    @DisplayName("Should return rewards for a specific customer")
    void shouldReturnCustomerRewardById() throws Exception {
        List<TransactionResponse> transactions = asList(
                new TransactionResponse(101L, 120.75, LocalDate.of(2024, 3, 15)),
                new TransactionResponse(102L, 90.00, LocalDate.of(2024, 4, 10))
        );

        CustomerRewardResponse mockResponse = new CustomerRewardResponse(
                1L, "John",Map.of("MARCH", 70, "APRIL", 80), 150, transactions
        );

        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 1);

        when(rewardService.getCustomerRewardById(1L, startDate, endDate)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/rewards/1")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.customerName").value("John"))
                .andExpect(jsonPath("$.totalRewards").value(150))
                .andExpect(jsonPath("$.monthlyRewards.MARCH").value(70))
                .andExpect(jsonPath("$.monthlyRewards.APRIL").value(80))
                .andExpect(jsonPath("$.transactions.size()").value(2));
    }

    @Test
    @DisplayName("Should fail when startDate is missing")
    void shouldReturnBadRequestOnMissingStartDate() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("endDate", "2024-06-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail on invalid date format")
    void shouldReturnBadRequestOnInvalidDate() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("startDate", "invalid-date")
                        .param("endDate", "2024-06-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
